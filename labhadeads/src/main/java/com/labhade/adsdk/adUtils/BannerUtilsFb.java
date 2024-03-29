package com.labhade.adsdk.adUtils;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.AdConstants;

public class BannerUtilsFb {

    public static int failed = 0;

    public static void show_banner(Context context, RelativeLayout bannerView) {

        if (AdConstants.adViewFb != null) {
            try {
                if (bannerView.getChildCount() > 0) {
                    bannerView.removeAllViews();
                }

                bannerView.addView(AdConstants.adViewFb);
            } catch (Exception e ) {
                e.printStackTrace();
            }

            loadFbBanner(context,bannerView,false);
        } else {
            loadFbBanner(context,bannerView,true);
        }

    }


    public static void loadFbBanner(Context context, RelativeLayout adContainer,boolean isFailed) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        AdView adView = new AdView(context, accountProvider.getFbBannerAds(), AdSize.BANNER_HEIGHT_50);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("BANNER_ERROR-->",adError.getErrorMessage());
                AdConstants.adViewFb = null;
                if (failed != 2) {
                    failed++;
                    loadFbBanner(context,adContainer,true);
                } else {
                    failed = 0;
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                failed = 0;
                if (isFailed) {
                    AdConstants.adViewFb = adView;
                    try {
                        if (adContainer.getChildCount() > 0) {
                            adContainer.removeAllViews();
                        }

                        adContainer.addView(adView);
                    } catch (Exception e ) {
                        e.printStackTrace();
                    }

                    loadFbBanner(context, adContainer,false);
                } else {
                    AdConstants.adViewFb = adView;
                }
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }

}
