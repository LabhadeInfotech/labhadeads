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
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.AdConstants;
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
                        AdConstants.isSplash = false;
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        AdConstants.isTimeFinish = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AdConstants.isTimeFinish = true;
                            }
                        }, myPref.getAdsTime() * 1000);

                        if (isFailed) {
                            if (LabhadeAds.isConnectingToInternet(mContext)) {
                                listener.onAdClose(true);
                            } else {
                                Toast.makeText(mContext, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        AdConstants.setCountDown();
                        AdConstants.isSplash = false;
                        if (isFailed) {
                            show_interstitial(interstitialAd);
                        } else {
                            AdConstants.interAdmob = interstitialAd;
                        }
                    }
                });

    }


    public void show_interstitial(InterstitialAd mInterstitialAd) {

        if (mInterstitialAd != null) {

            mInterstitialAd.show((Activity) mContext);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    AdConstants.isSplash = false;
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    AdConstants.isAdShowing = true;
                    AdConstants.dismissCount();
                    load_interstitial(false);
                    super.onAdShowedFullScreenContent();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    AdConstants.isTimeFinish = false;
                    AdConstants.isAdShowing = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AdConstants.isTimeFinish = true;
                        }
                    }, myPref.getAdsTime() * 1000);
                    listener.onAdClose(true);
                    super.onAdDismissedFullScreenContent();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    AdConstants.isSplash = false;

//                        show_interstitial(mInterstitialAd);
                    listener.onAdClose(true);
                }
            });
        } else {
            load_interstitial(true);
        }
    }
}