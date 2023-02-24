package com.labhade.adsdk.adUtils.wortiseAds;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.AdProgressDialog;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.LabhadeAds;
import com.labhade.adsdk.aditerface.Interstitial;

public class WortiseInterstitial {
    Context mContext;
    String mUnitId;
    private Dialog dialog = null;
    AdsAccountProvider myPref;
    Interstitial listener;

    public WortiseInterstitial(Context mContext, Interstitial listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public void load_interstitial(boolean isFailed) {
        myPref = new AdsAccountProvider(mContext);

        if (isFailed) {
            dialog = AdProgressDialog.show(mContext);
        }

        com.wortise.ads.interstitial.InterstitialAd interstitialAd =
                new com.wortise.ads.interstitial.InterstitialAd(mContext,myPref.getWortiseInter());

        interstitialAd.setListener(new com.wortise.ads.interstitial.InterstitialAd.Listener() {
            @Override
            public void onInterstitialClicked(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

            }

            @Override
            public void onInterstitialDismissed(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

            }

            @Override
            public void onInterstitialFailed(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd, @NonNull com.wortise.ads.AdError adError) {
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
                AdConstants.interWortise = null;
                if (isFailed) {
                    if (LabhadeAds.isConnectingToInternet(mContext)) {
                        listener.onAdClose(true);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onInterstitialLoaded(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                AdConstants.setCountDown();
                AdConstants.isSplash = false;
                if (isFailed) {
                    show_interstitial(interstitialAd, false);
                } else {
                    AdConstants.interWortise = interstitialAd;
                }
            }

            @Override
            public void onInterstitialShown(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

            }
        });

        interstitialAd.loadAd();

    }


    public void show_interstitial(com.wortise.ads.interstitial.InterstitialAd mInterstitialAd, boolean isFailed) {

        if (mInterstitialAd != null) {

            if (isFailed) {
                dialog = AdProgressDialog.show(mContext);
            }

            if (mInterstitialAd.isAvailable()) {
                mInterstitialAd.showAd();
            }

            mInterstitialAd.setListener(new com.wortise.ads.interstitial.InterstitialAd.Listener() {
                @Override
                public void onInterstitialClicked(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

                }

                @Override
                public void onInterstitialDismissed(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    AdConstants.isTimeFinish = false;
                    AdConstants.isAdShowing = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AdConstants.isTimeFinish = true;
                        }
                    }, myPref.getAdsTime() * 1000);
                    listener.onAdClose(true);
                }

                @Override
                public void onInterstitialFailed(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd, @NonNull com.wortise.ads.AdError adError) {}

                @Override
                public void onInterstitialLoaded(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

                }

                @Override
                public void onInterstitialShown(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                    AdConstants.interCount++;
                    AdConstants.isSplash = false;
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    AdConstants.isAdShowing = true;
                    AdConstants.dismissCount();
                    AdConstants.interWortise = null;
                    load_interstitial(false);
                }
            });
        } else {
            load_interstitial(true);
        }
    }

    public void loadAndShowInter() {

        dialog = AdProgressDialog.show(mContext);

        myPref = new AdsAccountProvider(mContext);

        com.wortise.ads.interstitial.InterstitialAd interstitialAd =
                new com.wortise.ads.interstitial.InterstitialAd(mContext,myPref.getWortiseInter());

        interstitialAd.setListener(new com.wortise.ads.interstitial.InterstitialAd.Listener() {
            @Override
            public void onInterstitialClicked(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {

            }

            @Override
            public void onInterstitialDismissed(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                AdConstants.interCount++;
                AdConstants.isAdShowing = false;
                AdConstants.isTimeFinish = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AdConstants.isTimeFinish = true;
                    }
                }, myPref.getAdsTime() * 1000);
                listener.onAdClose(true);
            }

            @Override
            public void onInterstitialFailed(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd, @NonNull com.wortise.ads.AdError adError) {
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

                if (LabhadeAds.isConnectingToInternet(mContext)) {
                    listener.onAdClose(true);
                } else {
                    Toast.makeText(mContext, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onInterstitialLoaded(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                if (interstitialAd.isAvailable()) {
                    interstitialAd.showAd();
                }
            }

            @Override
            public void onInterstitialShown(@NonNull com.wortise.ads.interstitial.InterstitialAd interstitialAd) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                AdConstants.isAdShowing = true;
            }
        });

        interstitialAd.loadAd();
    }

}