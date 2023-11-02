package com.example.dosa_store;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SSLHelper.handleSSLValidation();
    }
}
