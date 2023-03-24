package com.labhade.adsdk;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

/* MY APPLICATION*/

//@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//public void onAppBackgrounded() {
//        isAPPBackground = true;
//        }
//
//@OnLifecycleEvent(Lifecycle.Event.ON_START)
//public void onAppForegrounded() {
//        isAPPBackground = false;
//        }


//this.appOpenManager = new AppOpenManager(this);

//ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//        Forwarded
//
//public static boolean isAPPBackground = false;
public class AppOpenManager implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private static final String LOG_TAG = "AppOpenManager";
    private static boolean isShowingAd = false;
    private AppOpenAd appOpenAd = null;
    private AppOpenAd splashAds = null;
    private Activity currentActivity;
    AdsAccountProvider adsAccountProvider;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private final Application myApplication;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    public AppOpenManager(Application application) {
        this.myApplication = application;
        adsAccountProvider = new AdsAccountProvider(application);
        application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public interface OnAppOpenShowLister {
        void OnAdDismissed(boolean isFailed);
    }
    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }
        this.loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                AppOpenManager.this.appOpenAd = appOpenAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(AppOpenManager.LOG_TAG, "error in loading");
            }

        };
        AppOpenAd.load(this.myApplication, adsAccountProvider.getOpenAds(),getAdRequest(), 1, this.loadCallback);
    }

    public void loadSplashAds(Activity activity) {

        AppOpenAd.load(activity, adsAccountProvider.getOpenAds(),getAdRequest(), 1, new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                splashAds = appOpenAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

        });
    }


    public void showAdIfAvailable(Activity activity,OnAppOpenShowLister lister) {
        if (splashAds != null) {
            this.splashAds.show(this.currentActivity);
            splashAds.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    isShowingAd = false;
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    isShowingAd = false;
                    AppOpenManager.this.splashAds = null;
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                }
            });

            return;
        }

        Log.d(LOG_TAG, "Can not show ad.");
        fetchAd();
    }

    public void showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");
            this.appOpenAd.show(this.currentActivity);
            appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    isShowingAd = false;
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    isShowingAd = false;
                    AppOpenManager.this.appOpenAd = null;
                    AppOpenManager.this.fetchAd();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                }
            });

            return;
        }

        Log.d(LOG_TAG, "Can not show ad.");
        fetchAd();
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdAvailable() {
        return this.appOpenAd != null;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        this.currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        this.currentActivity = activity;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        this.currentActivity = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        showAdIfAvailable();
        Log.d(LOG_TAG, "onStart");
    }
}