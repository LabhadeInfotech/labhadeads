package com.labhade.adsdk;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.labhade.adsdk.adUtils.BannerUtils;
import com.labhade.adsdk.adUtils.BannerUtilsFb;
import com.labhade.adsdk.adUtils.InterstitialUtils;
import com.labhade.adsdk.adUtils.InterstitialUtilsFb;
import com.labhade.adsdk.adUtils.NativeUtils;
import com.labhade.adsdk.adUtils.NativeUtils40;
import com.labhade.adsdk.adUtils.NativeUtils50;
import com.labhade.adsdk.adUtils.NativeUtilsFb;
import com.labhade.adsdk.adUtils.RewardedUtils;
import com.labhade.adsdk.aditerface.Interstitial;


public class LabhadeAds {
   public static boolean  isClicked = false;

    public enum AdTemplate {
        NATIVE_300,
        NATIVE_100,
        NATIVE_50,
        NATIVE_40
    }


    public  static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void InitializeAds(Context context) {
        MobileAds.initialize(context, initializationStatus -> {});
        AudienceNetworkAds.initialize(context);
    }

    public static void setTestMode(Context context) {
        AdsAccountProvider adsAccountProvider = new AdsAccountProvider(context);

        adsAccountProvider.setAdsType("admob");
        adsAccountProvider.setBannerAds1("/6499/example/banner");
        adsAccountProvider.setInterAds1("/6499/example/interstitial");
        adsAccountProvider.setRewardAds1("/6499/example/rewarded");
        adsAccountProvider.setNativeAds1("/6499/example/native");
        adsAccountProvider.setOpenAds("/6499/example/app-open");
        adsAccountProvider.setAppOpenEnable(true);
        adsAccountProvider.setRewardEnable(true);
        adsAccountProvider.setInterEnable(true);
        adsAccountProvider.setAdsTime(1);
        adsAccountProvider.setBackAds(true);
    }


    public static void setDefault() {
        AdConstants.isPreloadedNative = false;
        AdConstants.isPreloadedFbNative = false;
        AdConstants.nativeAds = null;
        AdConstants.adView = null;
        AdConstants.adViewFb = null;
        AdConstants.nativeAdFb = null;
        AdConstants.interAdmob = null;
        AdConstants.interFb = null;
    }

    public static void showInterstitial(Context context, Interstitial listener) {

        if (isClicked()) {
            return;
        }

        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob") && myPref.isInterEnable()) {
            InterstitialUtils interstitialUtils = new InterstitialUtils(context,listener);
            interstitialUtils.show_interstitial(AdConstants.interAdmob);
        } else if ((myPref.getAdsType().equals("facebook")) && myPref.isInterEnable()) {
            InterstitialUtilsFb.loadInterstitial(context,listener);
        } else {
            listener.onAdClose(false);
        }
    }

    public static void showRewardAd(Context context, Interstitial callback) {
        if (isClicked()) {
            return;
        }

        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob") && myPref.isRewardEnabled()) {
            RewardedUtils.loadRewarded(context,callback);
        } else if (myPref.getAdsType().equals("admob")) {
            showInterstitial(context,callback);
        } else {
            callback.onAdClose(false);
        }
    }

    public static void showBanner(Context context, RelativeLayout bannerContainer) {
        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob")) {
            BannerUtils.show_banner(context, bannerContainer);
        } else if (myPref.getAdsType().equals("facebook")){
            BannerUtilsFb.show_banner(context, bannerContainer);
        }
    }

    public static void showNative(Context context, RelativeLayout nativeContainer, View space, AdTemplate adTemplate) {
        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob")) {
            if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                NativeUtils.showNative(context, nativeContainer, space, true);
            } else if (adTemplate.equals(AdTemplate.NATIVE_100)){
                NativeUtils.showNative(context, nativeContainer, space, false);
            } else if (adTemplate.equals(AdTemplate.NATIVE_50)){
                NativeUtils50.showNative(context, nativeContainer, space);
            } else {
                NativeUtils40.showNative(context, nativeContainer, space);
            }
        } else if (myPref.getAdsType().equals("facebook")) {
            if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                 NativeUtilsFb.showNativeFb(context, nativeContainer, space, true);
            } else if (adTemplate.equals(AdTemplate.NATIVE_100)){
                 NativeUtilsFb.showNativeFb(context, nativeContainer, space, false);
            }
        }
    }


    public static boolean isClicked() {
        if (isClicked) {
            return true;
        }
        isClicked = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isClicked = false;
            }
        },1500);
        return false;
    }

    public static void onBackPressed(Context context, Interstitial listener) {

        if (isClicked()) {
            return;
        }

        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob") && myPref.isBackAdsEnable()) {
            InterstitialUtils interstitialUtils = new InterstitialUtils(context,listener);
            interstitialUtils.show_interstitial(AdConstants.interAdmob);
        } else if (myPref.getAdsType().equals("facebook") &&  myPref.isBackAdsEnable()) {
            InterstitialUtilsFb.loadInterstitial(context,listener);
        } else {
            ((Activity) context).finish();
        }
    }
}
