package com.labhade.adsdk.adUtils.wortiseAds;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.labhade.adsdk.AdProgressDialog;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.aditerface.Interstitial;
import com.wortise.ads.rewarded.models.Reward;

public class WortiseReward {
    static Dialog dialog;

    public static void loadRewarded(Context context, Interstitial rewardInterface) {
        AdsAccountProvider adsAccountProvider = new AdsAccountProvider(context);

        dialog = AdProgressDialog.show(context);

        com.wortise.ads.rewarded.RewardedAd mRewarded = new com.wortise.ads.rewarded.RewardedAd(context,adsAccountProvider.getWortiseReward());

        mRewarded.setListener(new com.wortise.ads.rewarded.RewardedAd.Listener() {
            @Override
            public void onRewardedClicked(@NonNull com.wortise.ads.rewarded.RewardedAd rewardedAd) {

            }

            @Override
            public void onRewardedCompleted(@NonNull com.wortise.ads.rewarded.RewardedAd rewardedAd, @NonNull Reward reward) {

            }

            @Override
            public void onRewardedDismissed(@NonNull com.wortise.ads.rewarded.RewardedAd rewardedAd) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                rewardInterface.onAdClose(false);
            }

            @Override
            public void onRewardedFailed(@NonNull com.wortise.ads.rewarded.RewardedAd rewardedAd, @NonNull com.wortise.ads.AdError adError) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                rewardInterface.onAdClose(false);
            }

            @Override
            public void onRewardedLoaded(@NonNull com.wortise.ads.rewarded.RewardedAd rewardedAd) {
                if (mRewarded.isAvailable()) {
                    mRewarded.showAd();
                }
            }

            @Override
            public void onRewardedShown(@NonNull com.wortise.ads.rewarded.RewardedAd rewardedAd) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        mRewarded.loadAd();

    }
}
