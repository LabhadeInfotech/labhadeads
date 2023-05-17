package com.labhade.adsdk.adUtilsNew.admob;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.labhade.adsdk.AdsAccountProvider;

public class BannerAdmub {
    private AdView view;
    RelativeLayout relView;
    String mUnitId = "oa";
    Context ctx;
    static int adFail = 0;
    AdsAccountProvider adsAccountProvider;

    // Mode : auto show
    public BannerAdmub(Context ctx, RelativeLayout viewById) {
        this.ctx = ctx;
        this.relView = viewById;
        adsAccountProvider = new AdsAccountProvider(ctx);
        this.mUnitId = adsAccountProvider.getBannerAds1();

        requestView();
        addView();
        show();
    }

    void requestView() {

        try {
            view = new AdView(ctx);
            view.setAdSize(AdSize.BANNER);
            view.setAdUnitId(mUnitId);
            view.setAdListener(getAdListener());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void addView() {

        try {
            if (relView.getChildCount() > 0) {
                relView.removeAllViews();
            }
            relView.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private AdListener getAdListener() {

        AdListener adListener = new AdListener() {

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                if (adFail != 2) {
                    Log.e("TAG", "onAdFailedToLoad: "+adFail);
                    adFail++;
                    new BannerAdmub(ctx,relView);
                } else {
                    adFail = 0;
                }
            }


            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

        };
        return adListener;
    }

    private AdRequest getRequest() {
        return new Request().executeRequest();
    }

    public void show() {
        try {
            view.loadAd(getRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
