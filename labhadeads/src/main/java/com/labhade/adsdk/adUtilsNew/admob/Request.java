package com.labhade.adsdk.adUtilsNew.admob;

import android.os.AsyncTask;

import com.google.android.gms.ads.AdRequest;

public class Request {

    public AdRequest executeRequest() {
        AdRequest  request = new AdRequest.Builder().build();
        return request;
    }

}
