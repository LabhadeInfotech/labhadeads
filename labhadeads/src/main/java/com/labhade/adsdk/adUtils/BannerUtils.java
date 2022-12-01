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
import com.labhade.adsdk.Constants;


public class BannerUtils {
    static String mUnitId = null;

    public static void show_banner(Context context, RelativeLayout bannerView) {

        if (Constants.adView != null) {
            try {
                if (bannerView.getChildCount() > 0) {
                    bannerView.removeAllViews();
                }

                bannerView.addView(Constants.adView);
            }catch (Exception e) {
                e.printStackTrace();
            }

            load_ads(context, bannerView,false);
        } else {
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
                Constants.adView = null;
                load_ads(context, bannerView,true);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (isFailed ) {
                    try {
                        if (bannerView.getChildCount() > 0) {
                            bannerView.removeAllViews();
                        }

                        bannerView.addView(adView);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    Constants.adView = adView;
                    load_ads(context, bannerView,false);
                } else {
                    Constants.adView = adView;
                }
            }
        });

        adView.loadAd(new AdRequest.Builder().build());
    }

}
