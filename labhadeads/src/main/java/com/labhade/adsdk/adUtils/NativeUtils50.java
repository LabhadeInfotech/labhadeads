package com.labhade.adsdk.adUtils;

import android.content.Context;
import android.text.TextUtils;
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
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.R;

public class NativeUtils50 {

    public static String mUnitId;
    static int failed = 0;

    public static void load_native(Context context, RelativeLayout rlNative, View space) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getNativeAds1();

        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                failed = 0;
                if (!AdConstants.isPreloadedNative) {
                    AdConstants.isPreloadedNative = true;

                    try {
                        if (rlNative.getChildCount() > 0) {
                            rlNative.removeAllViews();
                        }

                        View view;
                        view = LayoutInflater.from(context).inflate(R.layout.ad_50, null);
                        populateNativeAd50(nativeAd, (NativeAdView) view.findViewById(R.id.native_ad_view));
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

                if (failed != 2) {
                    failed++;
                    load_native(context, rlNative, space);
                } else {
                    failed = 0;
                }
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
                view = LayoutInflater.from(context).inflate(R.layout.ad_50, null);
                populateNativeAd50(AdConstants.nativeAds, (NativeAdView) view.findViewById(R.id.native_ad_view));
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
                failed = 0;
                try {
                    if (rlNative.getChildCount() > 0) {
                        rlNative.removeAllViews();
                    }

                    View view;
                    view = LayoutInflater.from(context).inflate(R.layout.ad_50, null);
                    populateNativeAd50(nativeAd, (NativeAdView) view.findViewById(R.id.native_ad_view));
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
                if (failed != 2) {
                    failed++;
                    load_native(context, rlNative, space);
                } else {
                    failed = 0;
                }

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

    public static void populateNativeAd50(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

        try {
            NativeAdView nativeAdView = (NativeAdView) unifiedNativeAdView.findViewById(R.id.native_ad_view);
            TextView primaryView =  unifiedNativeAdView.findViewById(R.id.native_ad_title);
            TextView bodyText =  unifiedNativeAdView.findViewById(R.id.native_ad_body);
        RatingBar ratingBar = (RatingBar) unifiedNativeAdView.findViewById(R.id.ratingbar);
        ratingBar.setEnabled(false);
            Button callToActionView =  unifiedNativeAdView.findViewById(R.id.native_ad_call_to_action);
            ImageView iconView =  unifiedNativeAdView.findViewById(R.id.native_ad_icon);

            String headline = unifiedNativeAd.getHeadline();
            String body = unifiedNativeAd.getBody();
            String callToAction = unifiedNativeAd.getCallToAction();
            Double starRating = unifiedNativeAd.getStarRating();
            NativeAd.Image icon = unifiedNativeAd.getIcon();

            nativeAdView.setCallToActionView(callToActionView);
            nativeAdView.setHeadlineView(primaryView);
            primaryView.setText(headline);
            callToActionView.setText(callToAction);

        if (starRating != null && starRating > Double.longBitsToDouble(1)) {
            ratingBar.setVisibility(View.VISIBLE);
            ratingBar.setRating(starRating.floatValue());
            nativeAdView.setStarRatingView(ratingBar);
        } else {
            ratingBar.setVisibility(View.GONE);
        }
            if (icon != null) {
                iconView.setVisibility(View.VISIBLE);
                iconView.setImageDrawable(icon.getDrawable());
            } else {
                iconView.setVisibility(View.GONE);
            }
            if (bodyText != null) {
                bodyText.setText(body);
                nativeAdView.setBodyView(bodyText);
            }
            nativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
