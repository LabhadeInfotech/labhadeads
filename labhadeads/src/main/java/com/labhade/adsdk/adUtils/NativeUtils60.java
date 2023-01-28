package com.labhade.adsdk.adUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.R;

public class NativeUtils60 {

    public static String mUnitId;

    public static void load_native(Context context, RelativeLayout rlNative, View space) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getNativeAds1();

        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                if (!AdConstants.isPreloadedNative) {
                    AdConstants.isPreloadedNative = true;

                    try {
                        if (rlNative.getChildCount() > 0) {
                            rlNative.removeAllViews();
                        }

                        View view;
                        view = LayoutInflater.from(context).inflate(R.layout.ad_60, null);
                        populateNative60(nativeAd, (NativeAdView) view.findViewById(R.id.root_ad_view));
                        space.setVisibility(View.GONE);
                        rlNative.setVisibility(View.VISIBLE);
                        rlNative.removeAllViews();
                        rlNative.addView(view);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    load_native(context, rlNative, space);
                } else {
                    AdConstants.nativeAds = null;
                    AdConstants.nativeAds = nativeAd;
                }
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                AdConstants.nativeAds = null;
                try {
                    space.setVisibility(View.VISIBLE);
                    rlNative.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                load_native(context, rlNative, space);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void showNative(Context context, RelativeLayout rlNative, View space) {

        if (AdConstants.nativeAds != null) {

            try {
                if (rlNative.getChildCount() > 0) {
                    rlNative.removeAllViews();
                }

                View view;
                view = LayoutInflater.from(context).inflate(R.layout.ad_60, null);
                populateNative60(AdConstants.nativeAds, (NativeAdView) view.findViewById(R.id.root_ad_view));
                space.setVisibility(View.GONE);
                rlNative.setVisibility(View.VISIBLE);
                rlNative.removeAllViews();
                rlNative.addView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            AdConstants.nativeAds = null;
            load_native(context, rlNative, space);
        } else {
            AdConstants.nativeAds = null;
            AdConstants.isPreloadedNative = false;
            load_native(context, rlNative, space);
        }

    }

    public static void loadAndShowAds(Context context, RelativeLayout rlNative, View space) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getNativeAds1();

        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                try {
                    if (rlNative.getChildCount() > 0) {
                        rlNative.removeAllViews();
                    }

                    View view;
                    view = LayoutInflater.from(context).inflate(R.layout.ad_60, null);
                    populateNative60(nativeAd, (NativeAdView) view.findViewById(R.id.root_ad_view));
                    space.setVisibility(View.GONE);
                    rlNative.setVisibility(View.VISIBLE);
                    rlNative.removeAllViews();
                    rlNative.addView(view);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                try {
                    space.setVisibility(View.VISIBLE);
                    rlNative.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void populateNative60(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {


        try {
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.text_header));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.text_description));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.image_app));

            if (unifiedNativeAd.getHeadline() == null) {
                unifiedNativeAdView.getHeadlineView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getHeadlineView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }
            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
            if (unifiedNativeAd.getIcon() == null) {
                unifiedNativeAdView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
            }
            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
                unifiedNativeAdView.getCallToActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }

}
