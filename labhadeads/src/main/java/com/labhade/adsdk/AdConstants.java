package com.labhade.adsdk;

import android.os.CountDownTimer;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;

public  class AdConstants {
    public static boolean isTimeFinish = true;

    public static final String KEY_OPEN_ADS = "key_open_ads";
    public static final String KEY_NATIVE1 = "key_native_ads1";
    public static final String KEY_BANNER_ADS1 = "key_banner_ads1";
    public static final String KEY_INTERSTITIAL_ADS1 = "key_inter_ads1";
    public static final String KEY_APP_OPEN_ENABLE = "key_open_enable";
    public static final String KEY_REWARD_ENABLE = "key_reward_enable";
    public static final String KEY_REWARD_ADS = "key_reward_ads";
    public static final String KEY_ADS_TYPE = "key_ads_type";
    public static final String KEY_ADS_TIME = "key_ads_time";
    public static final String KEY_FB_BANNER = "key_banner_fb_ads";
    public static final String KEY_FB_NATIVE = "key_native_fb_ads";
    public static final String KEY_FB_INTER = "key_inter_fb_ads";
    public static final String KEY_BACK_PRESS = "key_backpress_ads";
    public static final String KEY_INTER_ENABLE = "key_inter_enable";

    public static final String KEY_LOAD_PRE = "key_pre_load";
    public static  boolean isAdShowing = false;
    public static  boolean isSplash = true;

    public static boolean isPreloadedNative = false;
    public static boolean isPreloadedFbNative = false;
    public static NativeAd nativeAds = null;
    public static AdView adView = null;
    public static com.facebook.ads.AdView adViewFb = null;
    public static com.facebook.ads.NativeAd nativeAdFb = null;
    public static InterstitialAd interAdmob = null;
    public static com.facebook.ads.InterstitialAd interFb = null;
    public static int interCount = 0;


    public static CountDownTimer mCountTimer = null;

    public static void setCountDown() {
       mCountTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                AdConstants.interAdmob = null;
                mCountTimer.cancel();
                mCountTimer = null;
            }
        };

       mCountTimer.start();
    }

    public static void dismissCount() {
        if (mCountTimer != null) {
            mCountTimer.cancel();
        }
    }

}
