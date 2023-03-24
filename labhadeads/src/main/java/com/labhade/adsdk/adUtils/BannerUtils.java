package com.labhade.adsdk.adUtils;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.AdConstants;


public class BannerUtils {
    static String mUnitId = null;
    static int adFail = 0;

    public static void show_banner(Context context, RelativeLayout bannerView) {

        if (AdConstants.adView != null) {
            try {
                if (bannerView.getChildCount() > 0) {
                    bannerView.removeAllViews();
                }

                bannerView.addView(AdConstants.adView);
            }catch (Exception e) {
                e.printStackTrace();
            }

            AdConstants.adView = null;
            load_ads(context, bannerView,false);
        } else {
            AdConstants.adView = null;
            load_ads(context, bannerView, true);
        }
    }

    public static void load_ads(Context context, RelativeLayout bannerView,boolean isFailed) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(context);
        mUnitId = accountProvider.getBannerAds1();
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(mUnitId);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                AdConstants.adView = null;
                if (adFail != 2) {
                    Log.e("TAG", "onAdFailedToLoad: "+adFail);
                    adFail++;
                    load_ads(context, bannerView, isFailed);
                } else {
                    adFail = 0;
                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adFail = 0;
                if (isFailed ) {
                    try {
                        if (bannerView.getChildCount() > 0) {
                            bannerView.removeAllViews();
                        }

                        bannerView.addView(adView);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    AdConstants.adView = null;
                    load_ads(context, bannerView,false);
                } else {
                    AdConstants.adView = null;
                    AdConstants.adView = adView;
                }
            }
        });

        adView.loadAd(new AdRequest.Builder().build());
    }

    public static void loadAndShowAds(Context context, RelativeLayout bannerView) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);
        mUnitId = accountProvider.getBannerAds1();

        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(mUnitId);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                if (adFail != 2) {
                    Log.e("TAG", "onAdFailedToLoad: "+adFail);
                    adFail++;
                    loadAndShowAds(context, bannerView);
                } else {
                    adFail = 0;
                }

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adFail = 0;
                try {
                    if (bannerView.getChildCount() > 0) {
                        bannerView.removeAllViews();
                    }
                    bannerView.addView(adView);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        adView.loadAd(new AdRequest.Builder().build());
    }


}
