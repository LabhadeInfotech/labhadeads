package com.labhade.adsdk.adUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
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
                       showRewarded(context,rewardedAd,rewardInterface);
                    }
                });
    }

    public static void showRewarded(Context context,RewardedAd rewardedAd,Interstitial rewardCallback) {

        rewardedAd.show((Activity) context, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                Toast.makeText(context, "Thank you for support with "+rewardItem.getAmount()
//                        + " and "+rewardItem.getType(), Toast.LENGTH_SHORT).show();
            }
        });


        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                rewardCallback.onAdClose(false);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                rewardCallback.onAdClose(false);
            }

            @Override
            public void onAdImpression() {}

            @Override
            public void onAdShowedFullScreenContent() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
}
