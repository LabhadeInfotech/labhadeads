package com.labhade.adsdk.adUtilsNew.admob;

//import android.content.Context;
//
//import android.util.Log;
//
//import com.facebook.ads.RewardedVideoAd;
//
//
//import java.util.concurrent.ExecutionException;
//
//
//public class Reward  {
//
//    private Context ctx;
//    private String UnitId = null;
//    private RewardedVideoAd mRewardedVideoAd;
//
//    // Mode : Auto
//    public Reward(Context ctx, String UnitId) throws ExecutionException, InterruptedException {
//        this.ctx = ctx;
//        this.UnitId = UnitId;
//        Init();
//        Request();
//        AddListener();
//    }
//
//    // Mode : Mannully
//    public Reward(Context ctx) {
//        this.ctx = ctx;
//        Init();
//    }
//
//    public String getUnitId() {
//        return UnitId;
//    }
//
//    public void setUnitId(String unitId) throws ExecutionException, InterruptedException {
//        UnitId = unitId;
//        Request();
//    }
//
//    private void Init()
//    {
//        mRewardedVideoAd = new RewardedVideoAd();
//    }
//
//    private void Request() throws ExecutionException, InterruptedException {
//        if (UnitId == null) {
//            UnitId = "ca-app-pub-3940256099942544/5224354917";
//        }
//        mRewardedVideoAd.loadAd();
//    }
//
//    private void AddListener(){
//        mRewardedVideoAd.se(new Listener("RewardedVideo".toUpperCase()));
//    }
//
//    public void show(){
//        if (mRewardedVideoAd.isLoaded()) {
//            mRewardedVideoAd.show();
//            Log.d("ADMOBREWARD", "ad loaded!");
//        }
//        else
//        {
//            Log.d("ADMOBREWARD", "ad wasn't loaded yet!");
//        }
//    }
//
//
//    class Listener extends RewardAdListener
//    {
//        public Listener(String TAG) {
//            super(TAG);
//        }
//
//        @Override
//        public void onRewardedVideoAdLoaded() {
//
//            if (mRewardedVideoAd.isLoaded()) {
//                mRewardedVideoAd.show();
//                super.onRewardedVideoAdLoaded();
//            } else {
//                onRewardedVideoAdFailedToLoad(1);
//            }
//        }
//    }
//
//
//
//
//}