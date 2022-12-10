package com.labhade.adsdk.adUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.labhade.adsdk.AdProgressDialog;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.Constants;
import com.labhade.adsdk.LabhadeAds;
import com.labhade.adsdk.aditerface.Interstitial;

public class InterstitialUtils {
    Context mContext;
    String mUnitId;
    private Dialog dialog = null;
    AdsAccountProvider myPref;
    Interstitial listener;

    public InterstitialUtils(Context mContext, Interstitial listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public void load_interstitial(boolean isFailed) {

        myPref = new AdsAccountProvider(mContext);

        InterstitialAd.load(mContext, mUnitId = myPref.getInterAds1()
                ,new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (LabhadeAds.isConnectingToInternet(mContext)) {
                            listener.onAdClose(true);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        Constants.setCountDown();
                        if (isFailed) {
                            show_interstitial(interstitialAd);
                        } else {
                            Constants.interAdmob = interstitialAd;
                        }
                    }
                });

    }


    public void show_interstitial(InterstitialAd mInterstitialAd) {

        if (Constants.isTimeFinish) {

            if (mInterstitialAd != null) {

                mInterstitialAd.show((Activity) mContext);
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        Constants.isSplash = false;
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Constants.isAdShowing = true;
                        Constants.dismissCount();
                        load_interstitial(false);

                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Constants.isTimeFinish = false;
                        Constants.isAdShowing = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Constants.isTimeFinish = true;
                            }
                        }, myPref.getAdsTime() * 1000);
                        listener.onAdClose(true);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        show_interstitial(mInterstitialAd);
//                listener.onAdClose(true);
                    }
                });
            } else {
                load_interstitial(true);
            }
        } else {
            listener.onAdClose(true);
        }
    }
}