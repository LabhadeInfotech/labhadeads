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
import com.labhade.adsdk.adUtils.NativeUtils350;
import com.labhade.adsdk.adUtils.NativeUtils40;
import com.labhade.adsdk.adUtils.NativeUtils50;
import com.labhade.adsdk.adUtils.NativeUtils60;
import com.labhade.adsdk.adUtils.NativeUtilsFb;
import com.labhade.adsdk.adUtils.RewardedUtils;
import com.labhade.adsdk.aditerface.Interstitial;

public class LabhadeAds {
   public static boolean  isClickedInter = false;
   public static boolean  isClickedBack = false;

    public enum AdTemplate {
        NATIVE_350,
        NATIVE_300,
        NATIVE_100,
        NATIVE_50,
        NATIVE_60,
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

        adsAccountProvider.setOpenAds("/6499/example/app-open");
        adsAccountProvider.setBannerAds1("/6499/example/banner");
        adsAccountProvider.setInterAds1("/6499/example/interstitial");
        adsAccountProvider.setRewardAds1("/6499/example/rewarded");
        adsAccountProvider.setNativeAds1("/6499/example/native");

        adsAccountProvider.setFbBannerAds("/6499/example/banner");
        adsAccountProvider.setFbInterAds("/6499/example/interstitial");
        adsAccountProvider.setFbNativeAds("/6499/example/native");

        adsAccountProvider.setPreload("load");

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

        if (isClickedInter()) {
            return;
        }

        if (AdConstants.isTimeFinish) {
            AdsAccountProvider myPref = new AdsAccountProvider(context);

            if (myPref.getAdsType().equals("admob") && myPref.isInterEnable()) {
                if (AdConstants.interCount != 6) {
                    InterstitialUtils interstitialUtils = new InterstitialUtils(context,listener);

                    if (myPref.getPreload().equals("pre")) {
                        interstitialUtils.show_interstitial(AdConstants.interAdmob,false);
                    } else {
                        interstitialUtils.loadAndShowInter();
                    }
                } else {
                    AdConstants.interCount = 0;
                    RewardedUtils.loadRewarded(context,listener);
                }
            } else if ((myPref.getAdsType().equals("facebook")) && myPref.isInterEnable()) {
                InterstitialUtilsFb.loadInterstitial(context,listener);
            } else {
                listener.onAdClose(false);
            }

        } else {
            listener.onAdClose(true);
        }


    }

    public static void showRewardAd(Context context, Interstitial callback) {

        if (isClickedInter()) {
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
            if (myPref.getPreload().equals("pre")) {
                BannerUtils.show_banner(context, bannerContainer);
            } else {
                BannerUtils.loadAndShowAds(context, bannerContainer);
            }
        } else if (myPref.getAdsType().equals("facebook")){
            BannerUtilsFb.show_banner(context, bannerContainer);
        } else {
            bannerContainer.getLayoutParams().height = 0;
        }
    }

    public static void showNative(Context context, RelativeLayout nativeContainer, View space, AdTemplate adTemplate) {
        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob")) {
            if (myPref.getPreload().equals("pre")) {
                if (adTemplate.equals(AdTemplate.NATIVE_350)) {
                    NativeUtils350.showNative(context, nativeContainer, space);
                } else if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                    NativeUtils.showNative(context, nativeContainer, space, true);
                } else if (adTemplate.equals(AdTemplate.NATIVE_100)){
                    NativeUtils.showNative(context, nativeContainer, space, false);
                }  else if (adTemplate.equals(AdTemplate.NATIVE_60)){
                    NativeUtils60.showNative(context, nativeContainer, space);
                }  else if (adTemplate.equals(AdTemplate.NATIVE_50)){
                    NativeUtils50.showNative(context, nativeContainer, space);
                } else {
                    NativeUtils40.showNative(context, nativeContainer, space);
                }
            } else {
                if (adTemplate.equals(AdTemplate.NATIVE_350)) {
                    NativeUtils350.loadAndShowAds(context, nativeContainer, space);
                } else if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                    NativeUtils.loadAndShowAds(context, nativeContainer, space, true);
                } else if (adTemplate.equals(AdTemplate.NATIVE_100)){
                    NativeUtils.loadAndShowAds(context, nativeContainer, space, false);
                }  else if (adTemplate.equals(AdTemplate.NATIVE_60)){
                    NativeUtils60.loadAndShowAds(context, nativeContainer, space);
                }  else if (adTemplate.equals(AdTemplate.NATIVE_50)){
                    NativeUtils50.loadAndShowAds(context, nativeContainer, space);
                } else {
                    NativeUtils40.loadAndShowAds(context, nativeContainer, space);
                }
            }

        } else if (myPref.getAdsType().equals("facebook")) {
            if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                 NativeUtilsFb.showNativeFb(context, nativeContainer, space, true);
            } else if (adTemplate.equals(AdTemplate.NATIVE_100)){
                 NativeUtilsFb.showNativeFb(context, nativeContainer, space, false);
            }
        } else {
            nativeContainer.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
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
        },1500);
        return false;
    }

    public static boolean isClickedBack() {
        if (isClickedBack) {
            return true;
        }
        isClickedBack = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isClickedBack = false;
            }
        },1500);
        return false;
    }

    public static void onBackPressed(Context context, Interstitial listener) {

        if (isClickedBack()) {
            return;
        }

        if (AdConstants.isTimeFinish) {
            AdsAccountProvider myPref = new AdsAccountProvider(context);

            if (myPref.getAdsType().equals("admob") && myPref.isBackAdsEnable()) {
                InterstitialUtils interstitialUtils = new InterstitialUtils(context,listener);

                if (myPref.getPreload().equals("pre")) {
                    interstitialUtils.show_interstitial(AdConstants.interAdmob,false);
                } else {
                    interstitialUtils.loadAndShowInter();
                }
            } else if (myPref.getAdsType().equals("facebook") &&  myPref.isBackAdsEnable()) {
                InterstitialUtilsFb.loadInterstitial(context,listener);
            } else {
                ((Activity) context).finish();
            }
        } else {
            listener.onAdClose(true);
        }


    }
}
