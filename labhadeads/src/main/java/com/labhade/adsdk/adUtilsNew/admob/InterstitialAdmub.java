package com.labhade.adsdk.adUtilsNew.admob;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.AdProgressDialog;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.adUtils.InterstitialUtils;
import com.labhade.adsdk.aditerface.Interstitial;

import java.util.concurrent.ExecutionException;

public class InterstitialAdmub {

    private InterstitialAd mInterstitialAd = null;
    private String unitId;
    private Context ctx;
    AdsAccountProvider adsAccountProvider;
    Interstitial mCallBack;
    private Dialog dialog = null;
    static int failed = 0;

    // Mode : Auto
    public InterstitialAdmub(Context ctx)  {
        adsAccountProvider = new AdsAccountProvider(ctx);
        this.unitId = adsAccountProvider.getInterAds1();
        this.ctx = ctx;
        RequestAd();
    }

    public InterstitialAdmub(){

    }

    // Mode : Mannully

    public void show(Interstitial callback) {
        mCallBack = callback;

        if (mInterstitialAd != null) {
            dialog = AdProgressDialog.show(ctx);

            mInterstitialAd.show((Activity) ctx);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
//                    AdConstants.isTimeFinish = false;
                    AdConstants.isAdShowing = false;
                    mInterstitialAd = null;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            AdConstants.isTimeFinish = true;
//                        }
//                    }, adsAccountProvider.getAdsTime() * 1000);
                    mCallBack.onAdClose(true);
                    super.onAdDismissedFullScreenContent();

                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    mInterstitialAd = null;
                    mCallBack.onAdClose(true);
                    super.onAdFailedToShowFullScreenContent(adError);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    mInterstitialAd = null;
                    AdConstants.isAdShowing = true;
                    dismissCount();
                    super.onAdShowedFullScreenContent();
                }
            });

        } else {
            RequestAndShow();
        }
    }


    private void RequestAndShow(){

        if (ctx != null) {
            InterstitialAd.load(ctx, unitId, new Request().executeRequest(), new InterstitialAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mInterstitialAd = null;
                    mCallBack.onAdClose(true);
                }

                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    mInterstitialAd = interstitialAd;
                    show(mCallBack);
                }
            });
        }

    }

    public static Handler handler;


     void setCountDown() {

        handler = new Handler();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mInterstitialAd = null;
                }
            },"mInterstitialAd",60000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mInterstitialAd = null;
                }
            },60000);
        }

    }

    void dismissCount() {
        if (handler != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                handler.removeCallbacksAndMessages("mInterstitialAd");
            } else {
                handler.removeCallbacksAndMessages(null);
            }
            mInterstitialAd = null;
        }
    }

    private void RequestAd(){

        InterstitialAd.load(ctx, unitId, new Request().executeRequest(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd = null;
                Toast.makeText(ctx, "failed", Toast.LENGTH_SHORT).show();

                if (failed != 3) {
                    failed++;
                    RequestAd();
                } else {
                    failed = 0;
                }
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                setCountDown();
                mInterstitialAd = interstitialAd;
                Toast.makeText(ctx, "Load", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public InterstitialAd getmInterstitialAd() {
        return mInterstitialAd;
    }

}

