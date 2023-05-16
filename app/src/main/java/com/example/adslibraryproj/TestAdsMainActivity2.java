package com.example.adslibraryproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.labhade.adsdk.FullNativeActivity;
import com.labhade.adsdk.LabhadeAds;

public class TestAdsMainActivity2 extends AppCompatActivity {
    RelativeLayout rlBanner,rlNative;
    View tvSpace;
    Button next,reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlBanner = findViewById(com.labhade.adsdk.R.id.rlBanner);
        rlNative = findViewById(com.labhade.adsdk.R.id.rlNative);
        tvSpace = findViewById(com.labhade.adsdk.R.id.tvSpace);
        next = findViewById(R.id.next);
        reward = findViewById(R.id.btn_click_here);

        LabhadeAds.loadInterstitial(this);


        LabhadeAds.showBanner(this,rlBanner);
        LabhadeAds.showNative(this,rlNative,tvSpace, LabhadeAds.AdTemplate.NATIVE_450);

        next.setOnClickListener(v -> LabhadeAds.showInterstitial(this, isFail -> {
            startActivity(new Intent(TestAdsMainActivity2.this, FullNativeActivity.class));
        }));

        reward.setOnClickListener(v -> LabhadeAds.showRewardAd(this, isFail -> {

        }));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, ""+intent.getAction(), Toast.LENGTH_SHORT).show();
    }
}