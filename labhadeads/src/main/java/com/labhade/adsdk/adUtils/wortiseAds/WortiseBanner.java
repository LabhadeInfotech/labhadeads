package com.labhade.adsdk.adUtils.wortiseAds;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.AdsAccountProvider;
import com.wortise.ads.AdError;
import com.wortise.ads.AdSize;
import com.wortise.ads.banner.BannerAd;

public class WortiseBanner {
    static String mUnitId = null;

    public static void show_banner(Context context, RelativeLayout bannerView) {

        if (AdConstants.bannerAd != null) {
            try {
                if (bannerView.getChildCount() > 0) {
                    bannerView.removeAllViews();
                }

                bannerView.addView(AdConstants.bannerAd);
            }catch (Exception e) {
                e.printStackTrace();
            }

            AdConstants.bannerAd = null;
            load_ads(context, bannerView,false);
        } else {
            AdConstants.bannerAd = null;
            load_ads(context, bannerView, true);
        }
    }

    public static void load_ads(Context context, RelativeLayout bannerView,boolean isFailed) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(context);
        mUnitId = accountProvider.getWortiseBanner();
        BannerAd adView = new BannerAd(context);
        adView.setAdSize(AdSize.HEIGHT_50);
        adView.setAdUnitId(mUnitId);

        adView.setListener(new BannerAd.Listener() {
            @Override
            public void onBannerClicked(@NonNull BannerAd bannerAd) {}

            @Override
            public void onBannerFailed(@NonNull BannerAd bannerAd, @NonNull AdError adError) {
                AdConstants.bannerAd = null;
                load_ads(context, bannerView, isFailed);
            }

            @Override
            public void onBannerLoaded(@NonNull BannerAd bannerAd) {
                if (isFailed ) {
                    try {
                        if (bannerView.getChildCount() > 0) {
                            bannerView.removeAllViews();
                        }

                        bannerView.addView(adView);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    AdConstants.bannerAd = null;
                    load_ads(context, bannerView,false);
                } else {
                    AdConstants.bannerAd = adView;
                }
            }
        });

        adView.loadAd();
    }

    public static void loadAndShowAds(Context context, RelativeLayout bannerView) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);
        mUnitId = accountProvider.getWortiseBanner();

        BannerAd adView = new BannerAd(context);
        adView.setAdSize(AdSize.HEIGHT_50);
        adView.setAdUnitId(mUnitId);

        adView.setListener(new BannerAd.Listener() {
            @Override
            public void onBannerClicked(@NonNull BannerAd bannerAd) {

            }

            @Override
            public void onBannerFailed(@NonNull BannerAd bannerAd, @NonNull AdError adError) {
                AdConstants.bannerAd = null;
                loadAndShowAds(context, bannerView);
            }

            @Override
            public void onBannerLoaded(@NonNull BannerAd bannerAd) {
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

        adView.loadAd();

    }


}