package com.labhade.adsdk;

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
import com.labhade.adsdk.adUtils.NativeUtilsFb;
import com.labhade.adsdk.adUtils.RewardedUtils;
import com.labhade.adsdk.aditerface.Interstitial;


public class LabhadeAds {
   public static boolean  isClicked = false;

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

        adsAccountProvider.setBannerAds1("/6499/example/banner");
        adsAccountProvider.setInterAds1("/6499/example/interstitial");
        adsAccountProvider.setRewardAds1("/6499/example/rewarded");
        adsAccountProvider.setAppOpenEnable(true);
        adsAccountProvider.setRewardEnable(true);
        adsAccountProvider.setInterAds1("/6499/example/interstitial");
        adsAccountProvider.setNativeAds1("/6499/example/native");
        adsAccountProvider.setAdsTime(1);
        adsAccountProvider.setOpenAds("/6499/example/app-open");
        adsAccountProvider.setAdsType("admob");
    }


    public static void setDefault() {
        Constants.isPreloadedNative = false;
        Constants.isPreloadedFbNative = false;
        Constants.nativeAds = null;
        Constants.adView = null;
        Constants.adViewFb = null;
        Constants.nativeAdFb = null;
        Constants.interAdmob = null;
        Constants.interFb = null;
    }

    public static void showInterstitial(Context context, Interstitial listener) {

        if (isClicked()) {
            return;
        }

        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob")) {
            InterstitialUtils interstitialUtils = new InterstitialUtils(context,listener);
            interstitialUtils.show_interstitial(Constants.interAdmob);
        } else if ((myPref.getAdsType().equals("facebook"))) {
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
        } else if (myPref.getAdsType().equals("admob") && !myPref.isRewardEnabled()) {
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

    public static void showNative(Context context, RelativeLayout nativeContainer, View space, boolean isBigNative) {
        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob")) {
            NativeUtils.showNative(context, nativeContainer,space,isBigNative);
        } else if (myPref.getAdsType().equals("facebook")) {
            NativeUtilsFb.showNativeFb(context, nativeContainer, space,isBigNative);
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
}
