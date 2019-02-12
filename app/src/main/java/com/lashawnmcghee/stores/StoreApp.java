/*
 * Copyright (C) 2018 LLM BottleRocket Store Test
 */
package com.lashawnmcghee.stores;

import android.app.Application;

import com.lashawnmcghee.stores.volley.StoresManager;

public class StoreApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //We just want to ensure our manager gets the application context on first init.
        StoresManager.getInstance(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
