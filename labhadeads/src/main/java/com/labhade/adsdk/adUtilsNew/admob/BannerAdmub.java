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
    private AdRequest Request;
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
        this.view = new AdView(ctx);
        view.setAdSize(AdSize.BANNER);
        view.setAdUnitId(mUnitId);
        view.setAdListener(getAdListener());
    }

    void addView() {
        if (relView.getChildCount() > 0) {
            relView.removeAllViews();
        }
        relView.addView(view);
    }

    public BannerAdmub() {
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
        view.loadAd(getRequest());
    }

    public AdView getView() {
        return view;
    }

    public void setView(View view) {
        this.view = (AdView) view;
    }
}
