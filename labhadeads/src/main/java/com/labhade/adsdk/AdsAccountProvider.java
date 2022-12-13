package com.labhade.adsdk;

import android.content.Context;
import android.content.SharedPreferences;



public class AdsAccountProvider {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AdsAccountProvider(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setFirstTime() {
        editor.putBoolean("isFirstTime", false);
        editor.commit();
        editor.apply();
    }

    public boolean isFirstTime() {
        return sharedPreferences.getBoolean("isFirstTime", true);
    }

    public void setOpenAds(String user) {
        sharedPreferences.edit().putString(AdConstants.KEY_OPEN_ADS, user).apply();
    }


    public String getOpenAds() {
        return sharedPreferences.getString(AdConstants.KEY_OPEN_ADS, "0a");
    }


    public void setFbBannerAds(String user) {
        sharedPreferences.edit().putString( AdConstants.KEY_FB_BANNER, user ).apply();
    }

    public String getFbBannerAds() {
        return sharedPreferences.getString( AdConstants.KEY_FB_BANNER, "0a" );

    }

    public void setFbInterAds(String user) {
        sharedPreferences.edit().putString( AdConstants.KEY_FB_INTER, user ).apply();
    }

    public String getFbInterAds() {
        return sharedPreferences.getString( AdConstants.KEY_FB_INTER, "0a" );

    }

    public void setFbNativeAds(String user) {
        sharedPreferences.edit().putString( AdConstants.KEY_FB_NATIVE, user ).apply();
    }

    public String getFbNativeAds() {
        return sharedPreferences.getString( AdConstants.KEY_FB_NATIVE, "0a" );

    }

    // Banner ads
    public void setBannerAds1(String user) {
        sharedPreferences.edit().putString( AdConstants.KEY_BANNER_ADS1, user ).apply();
    }

    public String getBannerAds1() {
        return sharedPreferences.getString( AdConstants.KEY_BANNER_ADS1, "0a" );

    }

    // Inter ads
    public void setInterAds1(String user) {
        sharedPreferences.edit().putString( AdConstants.KEY_INTERSTITIAL_ADS1, user ).apply();
    }

    public String getInterAds1() {
        return sharedPreferences.getString( AdConstants.KEY_INTERSTITIAL_ADS1, "0a" );

    }

    public void setAppOpenEnable(boolean isEnable) {
        sharedPreferences.edit().putBoolean( AdConstants.KEY_APP_OPEN_ENABLE, isEnable ).apply();
    }

    public boolean isAppOpenEnabled() {
        return sharedPreferences.getBoolean( AdConstants.KEY_APP_OPEN_ENABLE, false);

    }

    public void setRewardEnable(boolean isEnable) {
        sharedPreferences.edit().putBoolean( AdConstants.KEY_REWARD_ENABLE, isEnable ).apply();
    }

    public boolean isRewardEnabled() {
        return sharedPreferences.getBoolean( AdConstants.KEY_REWARD_ENABLE, true);

    }

    public void setRewardAds1(String user) {
        sharedPreferences.edit().putString( AdConstants.KEY_REWARD_ADS, user ).apply();
    }

    public String getRewardAds1() {
        return sharedPreferences.getString( AdConstants.KEY_REWARD_ADS, "0a" );

    }

    // Native ads
    public void setNativeAds1(String user) {
        sharedPreferences.edit().putString( AdConstants.KEY_NATIVE1, user ).apply();
    }

    public String getNativeAds1() {
        return sharedPreferences.getString( AdConstants.KEY_NATIVE1, "0a" );

    }

    public void setAdsTime(int user) {
        sharedPreferences.edit().putInt( AdConstants.KEY_ADS_TIME, user ).apply();
    }

    public int getAdsTime() {
        return sharedPreferences.getInt( AdConstants.KEY_ADS_TIME, 1);
    }

    public void setAdsType(String user) {
        sharedPreferences.edit().putString( AdConstants.KEY_ADS_TYPE, user ).apply();
    }

    public String getAdsType() {
        return sharedPreferences.getString( AdConstants.KEY_ADS_TYPE, "false" );

    }

    public void setBackAds(boolean isEnable) {
        sharedPreferences.edit().putBoolean( AdConstants.KEY_BACK_PRESS, isEnable ).apply();
    }

    public boolean isBackAdsEnable() {
        return sharedPreferences.getBoolean( AdConstants.KEY_BACK_PRESS, false);

    }
}
