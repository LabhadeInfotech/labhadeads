package com.labhade.adsdk.adViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.labhade.adsdk.AdsAccountProvider;
import com.labhade.adsdk.LabhadeAds;
import com.labhade.adsdk.adUtils.BannerUtils;
import com.labhade.adsdk.adUtils.BannerUtilsFb;
import com.labhade.adsdk.adUtilsNew.admob.BannerAdmub;
import com.labhade.adsdk.adUtilsNew.facebook.BannerFbNew;

public class BannerAdView extends RelativeLayout {

    public BannerAdView(Context context) {
        super(context);
    }

    public BannerAdView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setGravity(Gravity.CENTER);

        AdsAccountProvider myPref = new AdsAccountProvider(context);

        if (myPref.getAdsType().equals("admob")) {
            BannerUtils.loadAndShowAds(context, this);
        } else if (myPref.getAdsType().equals("facebook")) {
            new BannerFbNew(context,myPref.getFbBannerAds(),this);
        }else {
            this.setVisibility(GONE);
        }

    }

    public BannerAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BannerAdView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
