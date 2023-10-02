package com.example.adslibraryproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.labhade.adsdk.LabhadeAds;
import com.labhade.adsdk.baseAds.BaseAdsActivity;

public class TestAdsMainAdsActivity extends BaseAdsActivity {
    RelativeLayout rlBanner,rlNative;
    View tvSpace;
    Button next,reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LabhadeAds.InitializeAds(this);
        LabhadeAds.setTestMode(this);

        rlBanner = findViewById(com.labhade.adsdk.R.id.rlBanner);
        rlNative = findViewById(com.labhade.adsdk.R.id.rlNative);
        tvSpace = findViewById(com.labhade.adsdk.R.id.tvSpace);
        next = findViewById(R.id.next);
        reward = findViewById(R.id.btn_click_here);


        LabhadeAds.loadInterstitial(this);
        LabhadeAds.showNative(this,rlNative,tvSpace, LabhadeAds.AdTemplate.NATIVE_100);
        showBanner(rlBanner);

        next.setOnClickListener(v -> LabhadeAds.showInterstitial(this, isFail -> {
            startActivity(new Intent(TestAdsMainAdsActivity.this, TestAdsMainActivity2.class));
        }));

        reward.setOnClickListener(v -> LabhadeAds.showRewardAd(this, isFail -> {
            startActivity(new Intent(TestAdsMainAdsActivity.this, TestAdsMainActivity2.class));
        }));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, ""+intent.getPackage(), Toast.LENGTH_SHORT).show();
    }
}