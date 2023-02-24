package com.labhade.adsdk.adUtils.wortiseAds;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.labhade.adsdk.AdConstants;
import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.R;
import com.wortise.ads.AdError;
import com.wortise.ads.natives.GoogleNativeAd;

public class WortiseNative {
    public static String mUnitId;
    public static void load_native(Context context,RelativeLayout rlNative, View space,boolean isBigNative) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getWortiseNative();

        GoogleNativeAd  mGoogleNativeAd = new GoogleNativeAd(
                context, accountProvider.getWortiseNative(), new GoogleNativeAd.Listener() {
            @Override
            public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {

            }

            @Override
            public void onNativeFailed(@NonNull GoogleNativeAd googleNativeAd, @NonNull AdError adError) {
                AdConstants.googleNativeAds = null;
                try {
                    space.setVisibility(View.VISIBLE);
                    rlNative.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                load_native(context, rlNative, space,isBigNative);
            }

            @Override
            public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {

            }

            @Override
            public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
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
                    AdConstants.googleNativeAds = null;
                    AdConstants.googleNativeAds = nativeAd;
                }
            }
        });

        mGoogleNativeAd.load();


    }

    public static void showNative(Context context,RelativeLayout rlNative, View space,boolean isBigNative) {

        if (AdConstants.googleNativeAds != null) {
            try {
                if (rlNative.getChildCount() > 0) {
                    rlNative.removeAllViews();
                }

                View view;
                if (isBigNative) {
                    view = LayoutInflater.from(context).inflate(R.layout.ad_300, null);
                    populate300AppInstallAdViewMedia(AdConstants.googleNativeAds, (NativeAdView) view.findViewById(R.id.unified));
                } else {
                    view = LayoutInflater.from(context).inflate(R.layout.ad_100, null);
                    populateAppInstallAdViewMedia(AdConstants.googleNativeAds, (NativeAdView) view.findViewById(R.id.unified));
                }
                space.setVisibility(View.GONE);
                rlNative.setVisibility(View.VISIBLE);
                rlNative.removeAllViews();
                rlNative.addView(view);
            } catch (Exception e){
                e.printStackTrace();
            }
            AdConstants.googleNativeAds = null;
            load_native(context,rlNative,space,isBigNative);
        } else {
            AdConstants.isPreloadedNative = false;
            AdConstants.googleNativeAds = null;
            load_native(context,rlNative,space,isBigNative);
        }

    }


    public static void loadAndShowAds(Context context,RelativeLayout rlNative, View space,boolean isBigNative) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        mUnitId = accountProvider.getWortiseNative();

        GoogleNativeAd  mGoogleNativeAd = new GoogleNativeAd(
                context, accountProvider.getWortiseNative(), new GoogleNativeAd.Listener() {
            @Override
            public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {

            }

            @Override
            public void onNativeFailed(@NonNull GoogleNativeAd googleNativeAd, @NonNull AdError adError) {
                loadAndShowAds(context, rlNative, space, isBigNative);
                try {
                    space.setVisibility(View.VISIBLE);
                    rlNative.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {

            }

            @Override
            public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
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
        });

        mGoogleNativeAd.load();
    }


    public static void populateAppInstallAdViewMedia(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

        try {
            unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_icon));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
            unifiedNativeAdView.setImageView(unifiedNativeAdView.findViewById(R.id.ad_image));
            unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_rating));

            if (unifiedNativeAd.getIcon() == null) {
                ((ImageView) unifiedNativeAdView.getIconView()).setVisibility(View.INVISIBLE);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setVisibility(View.VISIBLE);
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            }

            if (unifiedNativeAd.getMediaContent() == null) {
                ((MediaView) unifiedNativeAdView.getMediaView()).setVisibility(View.GONE);
                if (unifiedNativeAd.getImages().size() > 0) {
                    ((ImageView) unifiedNativeAdView.getImageView()).setVisibility(View.INVISIBLE);
                } else {
                    ((ImageView) unifiedNativeAdView.getImageView()).setVisibility(View.VISIBLE);
                    ((ImageView) unifiedNativeAdView.getImageView()).setImageDrawable(unifiedNativeAd.getImages().get(0).getDrawable());
                }
            } else {
                ((MediaView) unifiedNativeAdView.getMediaView()).setVisibility(View.VISIBLE);
            }


            if (unifiedNativeAd.getHeadline() == null) {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.INVISIBLE);
            } else {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }

            if (unifiedNativeAd.getStore() == null) {
                ((TextView) unifiedNativeAdView.getStoreView()).setVisibility(View.GONE);
            } else {
                ((TextView) unifiedNativeAdView.getStoreView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getStoreView()).setText(unifiedNativeAd.getStore());
            }

            if (unifiedNativeAd.getAdvertiser() == null) {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setVisibility(View.GONE);
                if (unifiedNativeAd.getStarRating() == null) {
                    unifiedNativeAdView.getStarRatingView().setVisibility(View.GONE);
                } else {
                    unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
                    ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
                    unifiedNativeAdView.getStoreView().setVisibility(View.GONE);
                }
            } else {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
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
        }
    }

}