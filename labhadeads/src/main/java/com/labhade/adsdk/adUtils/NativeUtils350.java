package com.labhade.adsdk.adUtils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.R;

public class NativeUtils350 {

    public static String mUnitId;
    public static int failed = 0;
    static NativeAd nativeAd;

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
                        view = LayoutInflater.from(context).inflate(R.layout.ad_350, null);
                        populate350AppInstallAdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.root_ad_view));
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
                view = LayoutInflater.from(context).inflate(R.layout.ad_350, null);
                populate350AppInstallAdViewMedia(AdConstants.nativeAds, (NativeAdView) view.findViewById(R.id.root_ad_view));
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
                    boolean isDestroyed = false;       // && !(context instanceof MainActivity)


                    isDestroyed = ((Activity) context).isDestroyed();
                    if (NativeUtils.nativeAd != null) {
                        if (isDestroyed || ((Activity) context).isFinishing() || ((Activity) context).isChangingConfigurations()) {
                            NativeUtils.nativeAd.destroy();
                            return;
                        }
                    }

                    if (NativeUtils.nativeAd != null) {
                        NativeUtils.nativeAd.destroy();
                    }

                    NativeUtils.nativeAd = nativeAd;

                    View view;
                    view = LayoutInflater.from(context).inflate(R.layout.ad_350, null);
                    populate350AppInstallAdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.root_ad_view));
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
                    loadAndShowAds(context, rlNative, space);
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


    public static void populate350AppInstallAdViewMedia(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

        try {
            unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.media_content));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.text_header));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.text_description));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.image_app));
            unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ratingbar));
            unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.tv_advertiser));
            unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.tv_store));

            if (unifiedNativeAd.getHeadline() == null) {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.INVISIBLE);

            } else {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }

            if (unifiedNativeAd.getAdvertiser() == null) {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setVisibility(View.INVISIBLE);
            } else {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            }

            if (unifiedNativeAd.getStore() == null) {
                ((TextView) unifiedNativeAdView.getStoreView()).setVisibility(View.INVISIBLE);
            } else {
                ((TextView) unifiedNativeAdView.getStoreView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getStoreView()).setText(unifiedNativeAd.getStore());
            }

            if (unifiedNativeAd.getStarRating() == null) {
                unifiedNativeAdView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
                ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
                unifiedNativeAdView.getStoreView().setVisibility(View.INVISIBLE);
            }

            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }

            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
                unifiedNativeAdView.getCallToActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "onClick: " + "Native");
                    }
                });
            }
            if (unifiedNativeAd.getIcon() == null) {
                unifiedNativeAdView.getIconView().setVisibility(View.INVISIBLE);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

}
