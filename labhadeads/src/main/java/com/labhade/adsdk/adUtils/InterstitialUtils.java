package com.labhade.adsdk.adUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.LabhadeAds;
import com.labhade.adsdk.aditerface.Interstitial;

public class InterstitialUtils {
    Context mContext;
    String mUnitId;
    private Dialog dialog = null;
    AdsAccountProvider myPref;
    static int failed = 0;
    Interstitial listener;
    public static InterstitialAd mInterstitialAd = null;

    public InterstitialUtils(Context mContext, Interstitial listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public static void loadInterstitial(Context mContext) {
       AdsAccountProvider myPref = new AdsAccountProvider(mContext);

        AdRequest adRequest = new AdRequest.Builder().build();
        String mUnitId = myPref.getInterAds1();

        InterstitialAd.load(mContext,mUnitId
                ,adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);

                        mInterstitialAd = null;

                        if (failed != 3) {
                            failed++;
                            loadInterstitial(mContext);
                        } else {
                            failed = 0;
                        }

                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        failed = 0;
                        AdConstants.setCountDown();
                        mInterstitialAd = interstitialAd;
                    }
                });

    }


    public void showInterstitial() {

        myPref = new AdsAccountProvider(mContext);

        dialog = AdProgressDialog.show(mContext);

        if (mInterstitialAd != null) {
            mInterstitialAd.show((Activity) mContext);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    AdConstants.isAdShowing = true;
                    AdConstants.dismissCount();
                    mInterstitialAd  = null;
                    super.onAdShowedFullScreenContent();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    AdConstants.clickCount = 0;
                    AdConstants.isAdShowing = false;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            AdConstants.isTimeFinish = true;
//                        }
//                    }, myPref.getAdsTime() * 1000);
                    listener.onAdClose(true);
                    super.onAdDismissedFullScreenContent();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    mInterstitialAd = null;
                    listener.onAdClose(true);
                    super.onAdFailedToShowFullScreenContent(adError);
                }
            });
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            loadAndShowInter();
        }
    }

    public void loadAndShowInter() {

        dialog = AdProgressDialog.show(mContext);

        myPref = new AdsAccountProvider(mContext);

        AdRequest adRequest = new AdRequest.Builder().build();
        mUnitId = myPref.getInterAds1();

        InterstitialAd.load(mContext, mUnitId
                ,adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }

//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                AdConstants.isTimeFinish = true;
//                            }
//                        }, myPref.getAdsTime() * 1000);

                        if (LabhadeAds.isConnectingToInternet(mContext)) {
                            listener.onAdClose(true);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                       interstitialAd.show((Activity) mContext);
                       interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                           @Override
                           public void onAdDismissedFullScreenContent() {
                               super.onAdDismissedFullScreenContent();
                               if (dialog != null && dialog.isShowing()) {
                                   dialog.dismiss();
                               }
                               AdConstants.interCount++;
                               AdConstants.isAdShowing = false;
                               AdConstants.clickCount = 0;
//                               new Handler().postDelayed(new Runnable() {
//                                   @Override
//                                   public void run() {
//                                       AdConstants.isTimeFinish = true;
//                                   }
//                               }, myPref.getAdsTime() * 1000);
                               listener.onAdClose(true);
                           }

                           @Override
                           public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                               super.onAdFailedToShowFullScreenContent(adError);
                               if (dialog != null && dialog.isShowing()) {
                                   dialog.dismiss();
                               }
                               listener.onAdClose(false);
                           }

                           @Override
                           public void onAdShowedFullScreenContent() {
                               super.onAdShowedFullScreenContent();
                               if (dialog != null && dialog.isShowing()) {
                                   dialog.dismiss();
                               }
                               AdConstants.isAdShowing = true;
                           }
                       });
                    }
                });
    }

}