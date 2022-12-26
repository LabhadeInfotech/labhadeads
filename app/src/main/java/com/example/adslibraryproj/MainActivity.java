package com.example.adslibraryproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.labhade.adsdk.LabhadeAds;
import com.labhade.adsdk.aditerface.Interstitial;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rlBanner,rlNative;
    View tvSpace;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LabhadeAds.InitializeAds(this);

        rlBanner = findViewById(com.labhade.adsdk.R.id.rlBanner);
        rlNative = findViewById(com.labhade.adsdk.R.id.rlNative);
        tvSpace = findViewById(com.labhade.adsdk.R.id.tvSpace);
        next = findViewById(R.id.next);

        LabhadeAds.setDefault();
        LabhadeAds.setTestMode(this);
        LabhadeAds.showBanner(this,rlBanner);
        LabhadeAds.showNative(this,rlNative,tvSpace, LabhadeAds.AdTemplate.NATIVE_50);

        next.setOnClickListener(v -> LabhadeAds.showInterstitial(this, isFail -> {}));
    }
}