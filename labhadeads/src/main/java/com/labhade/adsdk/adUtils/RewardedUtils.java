package com.labhade.adsdk.adUtils;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.labhade.adsdk.AdProgressDialog;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.aditerface.Interstitial;

public class RewardedUtils {
    static Dialog dialog;

    public static void loadRewarded(Context context, Interstitial rewardInterface) {
        AdsAccountProvider adsAccountProvider = new AdsAccountProvider(context);

        dialog = AdProgressDialog.show(context);

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, adsAccountProvider.getRewardAds1(),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        rewardInterface.onAdClose(false);
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                       showRewarded(rewardedAd,rewardInterface);
                    }
                });
    }

    public static void showRewarded(RewardedAd rewardedAd,Interstitial rewardCallback) {
        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                rewardCallback.onAdClose(false);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                showRewarded(rewardedAd,rewardCallback);
            }

            @Override
            public void onAdImpression() {}

            @Override
            public void onAdShowedFullScreenContent() {
            }
        });
    }
}
