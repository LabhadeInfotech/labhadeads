package com.labhade.adsdk.adUtils;

import android.content.Context;
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
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.R;

public class NativeUtils40 {

    public static String mUnitId;
    public static int failed = 0;
    public static void load_native(Context context, RelativeLayout rlNative, View space) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getNativeAds1();
        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                failed = 0;
                if (!AdConstants.isPreloadedNative ) {
                    AdConstants.isPreloadedNative = true;

                    if (rlNative.getChildCount() > 0) {
                        rlNative.removeAllViews();
                    }

                    View view = LayoutInflater.from(context).inflate( R.layout.ad_40, null);
                    populateNative40(nativeAd, (NativeAdView) view.findViewById(R.id.unified));
                    space.setVisibility(View.GONE);
                    rlNative.setVisibility(View.VISIBLE);
                    rlNative.removeAllViews();
                    rlNative.addView(view);

                    AdConstants.nativeAds = null;
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

    public static void showNative(Context context,RelativeLayout rlNative, View space) {

        if (AdConstants.nativeAds != null) {
            if (rlNative.getChildCount() > 0) {
                rlNative.removeAllViews();
            }



            View view = LayoutInflater.from(context).inflate( R.layout.ad_40, null);
            populateNative40(AdConstants.nativeAds, (NativeAdView) view.findViewById(R.id.unified));
            space.setVisibility(View.GONE);
            rlNative.setVisibility(View.VISIBLE);
            rlNative.removeAllViews();
            rlNative.addView(view);
            AdConstants.nativeAds = null;
            load_native(context,rlNative,space);
        } else {
            AdConstants.isPreloadedNative = false;
            load_native(context,rlNative,space);
        }

    }

    public static void loadAndShowAds(Context context, RelativeLayout rlNative, View space) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getNativeAds1();
        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                failed = 0;
                if (rlNative.getChildCount() > 0) {
                    rlNative.removeAllViews();
                }



                View view = LayoutInflater.from(context).inflate( R.layout.ad_40, null);
                populateNative40(nativeAd, (NativeAdView) view.findViewById(R.id.unified));
                space.setVisibility(View.GONE);
                rlNative.setVisibility(View.VISIBLE);
                rlNative.removeAllViews();
                rlNative.addView(view);
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
                loadAndShowAds(context, rlNative, space);
                space.setVisibility(View.VISIBLE);
                rlNative.setVisibility(View.GONE);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }


    public static void populateNative40(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        String Store;
        String Adverstiser;
        Double ratingDoubleValue;
        Float rating;

        try {
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.txt_headline));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.txt_subtitle));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.btn_click_here));
            unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ratingbar));
            unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.txt_store));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.iv_icon));

            unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.txt_store));

            Store = String.valueOf(unifiedNativeAd.getStore());
            Adverstiser = String.valueOf(unifiedNativeAd.getAdvertiser());


            if (unifiedNativeAd.getStarRating() == null) {
                unifiedNativeAdView.getStarRatingView().setVisibility(View.GONE);
                unifiedNativeAdView.getStoreView().setVisibility(View.VISIBLE);

                if (Store == null) {
                    Store = Adverstiser;
                    ((TextView) unifiedNativeAdView.getStoreView()).setText(Adverstiser);
                } else if (Adverstiser == null) {
                    Store = " ";
                    ((TextView) unifiedNativeAdView.getStoreView()).setText(Store);
                } else {
                    ((TextView) unifiedNativeAdView.getStoreView()).setText(Adverstiser);
                }

            } else {
                unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
                ratingDoubleValue = unifiedNativeAd.getStarRating();
                rating = ratingDoubleValue.floatValue();
                ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(rating);
                unifiedNativeAdView.getStoreView().setVisibility(View.GONE);
            }

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