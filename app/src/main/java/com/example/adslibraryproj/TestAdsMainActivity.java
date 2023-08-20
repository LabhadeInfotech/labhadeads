package com.example.adslibraryproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.labhade.adsdk.AdProgressDialog;
import com.labhade.adsdk.LabhadeAds;

public class TestAdsMainActivity extends AppCompatActivity {
    RelativeLayout rlBanner,rlNative;
    View tvSpace;
    Button next,reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LabhadeAds.setTestMode(this);
        setContentView(R.layout.activity_main);

        LabhadeAds.InitializeAds(this);

        rlBanner = findViewById(com.labhade.adsdk.R.id.rlBanner);
        rlNative = findViewById(com.labhade.adsdk.R.id.rlNative);
        tvSpace = findViewById(com.labhade.adsdk.R.id.tvSpace);
        next = findViewById(R.id.next);
        reward = findViewById(R.id.btn_click_here);


        LabhadeAds.loadInterstitial(this);

        LabhadeAds.showBanner(this,rlBanner);
        LabhadeAds.showNative(this,rlNative,tvSpace, LabhadeAds.AdTemplate.NATIVE_100);

        next.setOnClickListener(v -> LabhadeAds.showInterstitial(this, isFail -> {
            startActivity(new Intent(TestAdsMainActivity.this, TestAdsMainActivity2.class));
        }));

        reward.setOnClickListener(v -> LabhadeAds.showRewardAd(this, isFail -> {
            startActivity(new Intent(TestAdsMainActivity.this, TestAdsMainActivity2.class));
        }));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, ""+intent.getPackage(), Toast.LENGTH_SHORT).show();
    }
}