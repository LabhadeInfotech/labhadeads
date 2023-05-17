package com.labhade.adsdk.adUtils;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.R;

public class NativeAdUtils250 {
    public static NativeAd nativeAd;

    public static void populateNativeAdView250(NativeAd nativeAd, NativeAdView adView) {

        try {
            adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
            adView.setBodyView(adView.findViewById(R.id.ad_body));
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
            adView.setIconView(adView.findViewById(R.id.ad_app_icon));
            adView.setPriceView(adView.findViewById(R.id.ad_price));
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
            adView.setStoreView(adView.findViewById(R.id.ad_store));
            adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

            if (nativeAd.getBody() == null) {
                adView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                adView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }

            if (nativeAd.getCallToAction() == null) {
                adView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                adView.getCallToActionView().setVisibility(View.VISIBLE);
                ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            }

            if (nativeAd.getIcon() == null) {
                adView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(
                        nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }

            if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }

            if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
            }

            if (nativeAd.getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView())
                        .setRating(nativeAd.getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }

            if (nativeAd.getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.INVISIBLE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }

            adView.setNativeAd(nativeAd);

            VideoController vc = nativeAd.getMediaContent().getVideoController();

            if (vc.hasVideoContent()) {
                vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                    @Override
                    public void onVideoEnd() {
                        super.onVideoEnd();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void loanNative250(View space,RelativeLayout frameLayout, Activity activity) {
        AdsAccountProvider adsAccountProvider = new AdsAccountProvider(activity);
        String unitId = adsAccountProvider.getNativeAds1();
        AdLoader.Builder builder = new AdLoader.Builder(activity, unitId);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                boolean isDestroyed = false;

                isDestroyed = activity.isDestroyed();
                if (NativeAdUtils250.nativeAd != null) {
                    if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                        nativeAd.destroy();
                        return;
                    }
                }

                if (NativeAdUtils250.nativeAd != null) {
                    NativeAdUtils250.nativeAd.destroy();
                }
                NativeAdUtils250.nativeAd = nativeAd;

                View adView = activity.getLayoutInflater().inflate(R.layout.ad_250, null);
                populateNativeAdView250(nativeAd, (NativeAdView) adView.findViewById(R.id.unified_native_view));

                try {
                    if (frameLayout.getChildCount() > 0) {
                        frameLayout.removeAllViews();
                    }
                    frameLayout.addView(adView);

                    frameLayout.setVisibility(View.VISIBLE);
                    space.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        AdLoader adLoader = builder.withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {

                        try {
                            if (!(space instanceof ImageView)) {
                                frameLayout.setVisibility(View.GONE);
                                space.setVisibility(View.VISIBLE);
                            } else {
                                frameLayout.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }
}