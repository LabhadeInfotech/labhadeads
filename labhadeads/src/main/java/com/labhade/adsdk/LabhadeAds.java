package com.labhade.adsdk;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.labhade.adsdk.adUtils.BannerUtils;
import com.labhade.adsdk.adUtils.BannerUtilsFb;
import com.labhade.adsdk.adUtils.InterstitialUtils;
import com.labhade.adsdk.adUtils.InterstitialUtilsFb;
import com.labhade.adsdk.adUtils.NativeAdUtils250;
import com.labhade.adsdk.adUtils.NativeUtils;
import com.labhade.adsdk.adUtils.NativeUtils350;
import com.labhade.adsdk.adUtils.NativeUtils40;
import com.labhade.adsdk.adUtils.NativeUtils450;
import com.labhade.adsdk.adUtils.NativeUtils50;
import com.labhade.adsdk.adUtils.NativeUtils60;
import com.labhade.adsdk.adUtils.NativeUtilsFb;
import com.labhade.adsdk.adUtils.RewardedUtils;
import com.labhade.adsdk.adUtilsNew.admob.BannerAdmub;
import com.labhade.adsdk.adUtilsNew.admob.InterstitialAdmub;
import com.labhade.adsdk.adUtilsNew.facebook.BannerFbNew;
import com.labhade.adsdk.aditerface.Banner;
import com.labhade.adsdk.aditerface.Interstitial;

public class LabhadeAds {
    public static boolean isClickedInter = false;

    public enum AdTemplate {
        NATIVE_350,
        NATIVE_300,
        NATIVE_250,
        NATIVE_450,
        NATIVE_100,
        NATIVE_50,
        NATIVE_60,
        NATIVE_40
    }


    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void InitializeAds(Context context) {
        MobileAds.initialize(context, initializationStatus -> {
        });
        AudienceNetworkAds.initialize(context);
    }

    public static void setTestMode(Context context) {
        AdsAccountProvider adsAccountProvider = new AdsAccountProvider(context);

        adsAccountProvider.setAdsType("admob");

        adsAccountProvider.setOpenAds("/6499/example/app-open");
        adsAccountProvider.setBannerAds1("/6499/example/banner");
        adsAccountProvider.setInterAds1("/6499/example/interstitial");
        adsAccountProvider.setRewardAds1("/6499/example/rewarded");
        adsAccountProvider.setNativeAds1("/6499/example/native");

        adsAccountProvider.setFbBannerAds("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        adsAccountProvider.setFbInterAds("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        adsAccountProvider.setFbNativeAds("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");

        adsAccountProvider.setPreload("pre");

        adsAccountProvider.setAppOpenEnable(true);
        adsAccountProvider.setRewardEnable(true);
        adsAccountProvider.setInterEnable(true);
        adsAccountProvider.setAdsTime(0);
        adsAccountProvider.setBackAds(true);
    }


    public static void loadInterstitial(Context mContext) {
        AdsAccountProvider myPref = new AdsAccountProvider(mContext);
        if (myPref.getAdsType().equals("admob") && myPref.isInterEnable() && myPref.getPreload().equals("pre")) {
            if (AdConstants.clickCount == myPref.getAdsTime()) {
                if (InterstitialUtils.mInterstitialAd == null) {
                    InterstitialUtils.loadInterstitial(mContext);
                }
            }
        }
    }

//    public static void loadInterstitialAd(Context context) {
//        AdsAccountProvider myPref = new AdsAccountProvider(context);
//        if (myPref.getAdsType().equals("admob") && myPref.isInterEnable()) {
//            new InterstitialAdmub(context);
//        }
//    }

    public static void showInterstitial(Context context, Interstitial listener) {

        if (isClickedInter()) {
            return;
        }

        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (AdConstants.clickCount == myPref.getAdsTime()) {
            AdConstants.clickCount = 0;
            if (myPref.getAdsType().equals("admob") && myPref.isInterEnable()) {
                InterstitialUtils interstitialUtils = new InterstitialUtils(context, listener);

                if (myPref.getPreload().equals("pre")) {
                    interstitialUtils.showInterstitial();
                } else {
                    interstitialUtils.loadAndShowInter();
                }
            } else if ((myPref.getAdsType().equals("facebook")) && myPref.isInterEnable()) {
                InterstitialUtilsFb.loadInterstitial(context, listener);
            } else {
                listener.onAdClose(false);
            }

        } else {
            AdConstants.clickCount++;
            listener.onAdClose(true);
        }


    }

    public static void showRewardAd(Context context, Interstitial callback) {

        if (isClickedInter()) {
            return;
        }

        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob") && myPref.isRewardEnabled()) {
            RewardedUtils.loadRewarded(context, callback);
        } else if (myPref.getAdsType().equals("admob")) {
            showInterstitial(context, callback);
        } else {
            callback.onAdClose(false);
        }
    }

    public static void showBanner(Context context, RelativeLayout bannerContainer) {
        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob")) {
            if (myPref.getPreload().equals("pre")) {
                new BannerAdmub(context, bannerContainer);
            } else {
                BannerUtils.loadAndShowAds(context, bannerContainer);
            }
        } else if (myPref.getAdsType().equals("facebook")) {
            if (myPref.getPreload().equals("pre")) {
                new BannerFbNew(context,myPref.getFbBannerAds(),bannerContainer);
            } else {
                BannerUtilsFb.show_banner(context, bannerContainer);
            }
        } else {
            bannerContainer.getLayoutParams().height = 0;
        }
    }

    public static void showNative(Context context, RelativeLayout nativeContainer, View space, AdTemplate adTemplate) {
        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob")) {

            if (adTemplate.equals(AdTemplate.NATIVE_350)) {
                NativeUtils350.loadAndShowAds(context, nativeContainer, space);
            } else if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                NativeUtils.loadAndShowAds(context, nativeContainer, space, true);
            } else if (adTemplate.equals(AdTemplate.NATIVE_450)) {
                NativeUtils450.loadNative450(space, nativeContainer, (Activity) context);
            } else if (adTemplate.equals(AdTemplate.NATIVE_100)) {
                NativeUtils.loadAndShowAds(context, nativeContainer, space, false);
            } else if (adTemplate.equals(AdTemplate.NATIVE_40)) {
                NativeUtils40.loadAndShowAds(context, nativeContainer, space);
            } else if (adTemplate.equals(AdTemplate.NATIVE_60)) {
                NativeUtils60.loadAndShowAds(context, nativeContainer, space);
            } else if (adTemplate.equals(AdTemplate.NATIVE_50)) {
                NativeUtils50.loadAndShowAds(context, nativeContainer, space);
            } else {
                NativeAdUtils250.loanNative250(space, nativeContainer, (Activity) context);
            }

        } else if (myPref.getAdsType().equals("facebook")) {
            if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                NativeUtilsFb.loadFbNative(context, nativeContainer, space, true);
            } else if (adTemplate.equals(AdTemplate.NATIVE_100)) {
                NativeUtilsFb.loadFbNative(context, nativeContainer, space, false);
            }
        } else {
            if (!(space instanceof ImageView)) {
                space.setVisibility(View.GONE);
            }
            nativeContainer.setVisibility(View.GONE);
        }
    }

    public static boolean isClickedInter() {
        if (isClickedInter) {
            return true;
        }
        isClickedInter = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isClickedInter = false;
            }
        }, 1500);
        return false;
    }

}
