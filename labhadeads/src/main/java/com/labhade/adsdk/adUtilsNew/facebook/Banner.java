package com.labhade.adsdk.adUtilsNew.facebook;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.labhade.adsdk.AdConstants;


public class Banner {

    private AdView adView;
    private RelativeLayout view;
    private Context ctx;
    private String unitId;
    public static int failed = 0;


    // Mode : Auto
    public Banner(Context ctx, String UnitId , RelativeLayout view) {
        this.ctx = ctx;
        this.unitId = UnitId;
        this.view = view;

        Init();
        AddView();
        Request();

    }

    // Mode : Mannully
    public Banner(Context ctx,String UnitId) {
        this.ctx = ctx;
        this.unitId = UnitId;
        Init();
    }

    public View getView() {
        return view;
    }

    public void setView(RelativeLayout view) {
        this.view = view;
        AddView();
    }

    public void show()
    {
        Request();
    }

    private void Request()
    {
        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("BANNER_ERROR-->",adError.getErrorMessage());
                if (failed != 2) {
                    failed++;
                    Request();
                } else {
                    failed = 0;
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                failed = 0;
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }

    private void AddView() {

        // Add the ad view to your activity layout

        if (view.getChildCount() > 0) {
            view.removeAllViews();
        }
        view.addView(adView);
    }

    private void Init() {

        adView = new AdView(ctx, unitId, AdSize.BANNER_HEIGHT_50);
    }


}
