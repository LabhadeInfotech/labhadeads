package com.labhade.adsdk.adUtils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.R;

public class NativeUtils {

    public static String mUnitId;
    public static void load_native(Context context,RelativeLayout rlNative, View space,boolean isBigNative) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getNativeAds1();

        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                                                // && !(context instanceof MainActivity)
                if (!AdConstants.isPreloadedNative) {
                    AdConstants.isPreloadedNative = true;

                    try {
                        if (rlNative.getChildCount() > 0) {
                            rlNative.removeAllViews();
                        }

                        View view;
                        if (isBigNative) {
                            view = LayoutInflater.from(context).inflate(R.layout.ad_300, null);
                            populate300AppInstallAdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.unified));
                        } else {
                            view = LayoutInflater.from(context).inflate(R.layout.ad_100, null);
                            populateAppInstallAdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.unified));
                        }
                        space.setVisibility(View.GONE);
                        rlNative.setVisibility(View.VISIBLE);
                        rlNative.removeAllViews();
                        rlNative.addView(view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    load_native(context, rlNative, space, isBigNative);
                } else {
                    AdConstants.nativeAds = null;
                    AdConstants.nativeAds = nativeAd;
                }
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                AdConstants.nativeAds = null;
                try {
                    space.setVisibility(View.VISIBLE);
                    rlNative.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                load_native(context, rlNative, space,isBigNative);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void showNative(Context context,RelativeLayout rlNative, View space,boolean isBigNative) {

        if (AdConstants.nativeAds != null) {
            try {
                if (rlNative.getChildCount() > 0) {
                    rlNative.removeAllViews();
                }

                View view;
                if (isBigNative) {
                    view = LayoutInflater.from(context).inflate(R.layout.ad_300, null);
                    populate300AppInstallAdViewMedia(AdConstants.nativeAds, (NativeAdView) view.findViewById(R.id.unified));
                } else {
                    view = LayoutInflater.from(context).inflate(R.layout.ad_100, null);
                    populateAppInstallAdViewMedia(AdConstants.nativeAds, (NativeAdView) view.findViewById(R.id.unified));
                }
                space.setVisibility(View.GONE);
                rlNative.setVisibility(View.VISIBLE);
                rlNative.removeAllViews();
                rlNative.addView(view);
            } catch (Exception e){
                e.printStackTrace();
            }
            AdConstants.nativeAds = null;
            load_native(context,rlNative,space,isBigNative);
        } else {
            AdConstants.isPreloadedNative = false;
            AdConstants.nativeAds = null;
            load_native(context,rlNative,space,isBigNative);
        }

    }


    public static void loadAndShowAds(Context context,RelativeLayout rlNative, View space,boolean isBigNative) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getNativeAds1();

        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                try {
                    if (rlNative.getChildCount() > 0) {
                        rlNative.removeAllViews();
                    }

                    View view;
                    if (isBigNative) {
                        view = LayoutInflater.from(context).inflate(R.layout.ad_300, null);
                        populate300AppInstallAdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.unified));
                    } else {
                        view = LayoutInflater.from(context).inflate(R.layout.ad_100, null);
                        populateAppInstallAdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.unified));
                    }
                    space.setVisibility(View.GONE);
                    rlNative.setVisibility(View.VISIBLE);
                    rlNative.removeAllViews();
                    rlNative.addView(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loadAndShowAds(context, rlNative, space, isBigNative);
                try {
                    space.setVisibility(View.VISIBLE);
                    rlNative.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }


    public static void populateAppInstallAdViewMedia(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

//        if (unifiedNativeAd.getIcon() == null) {
//            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
//        } else {
//            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
//            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
//        }
        try {
            unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
//        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
            if (unifiedNativeAd.getHeadline() == null) {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.INVISIBLE);
            } else {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }

            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
                unifiedNativeAdView.getCallToActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "onClick: " + "Native");
                    }
                });
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void populate300AppInstallAdViewMedia(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

        try {
            unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
            if (unifiedNativeAd.getHeadline() == null) {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            } else {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }

            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
                unifiedNativeAdView.getCallToActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "onClick: " + "Native");
                    }
                });
            }
            if (unifiedNativeAd.getIcon() == null) {
                unifiedNativeAdView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e2) {
            e2.printStackTrace();
            return;
        }
    }

}