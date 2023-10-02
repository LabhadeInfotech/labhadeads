package com.labhade.adsdk.adUtils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.labhade.adsdk.AdProgressDialog;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.LabhadeAds;
import com.labhade.adsdk.aditerface.Interstitial;


public class InterstitialUtilsFb {

    static int failed = 0;
    public static void loadInterstitial(Context mContext, Interstitial listener) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(mContext);

        Dialog dialog = AdProgressDialog.show(mContext);
        InterstitialAd interstitialAd = new InterstitialAd(mContext,accountProvider.getFbInterAds());

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                AdConstants.isAdShowing = true;
                dialog.dismiss();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                listener.onAdClose(false);
                AdConstants.isAdShowing = false;
            }


            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("INTER_ERROR-->", "Interstitial ad failed to load: " + adError.getErrorMessage());
                dialog.dismiss();
                listener.onAdClose(true);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                failed = 0;
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {}

            @Override
            public void onLoggingImpression(Ad ad) {}
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }
}