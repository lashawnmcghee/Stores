/*
 * Copyright (C) 2018 LLM BottleRocket Store Test
 */
package com.lashawnmcghee.stores.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lashawnmcghee.stores.interfaces.IGlobalDefines;
import com.lashawnmcghee.stores.interfaces.IStoresManagerListener;
import com.lashawnmcghee.stores.models.StoreModel;
import com.lashawnmcghee.stores.models.StoresJsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoresManager implements IGlobalDefines {
    private static final String TAG = StoresManager.class.getSimpleName();

    private static StoresManager instance = null;
    private Context mContext;
    private List<IStoresManagerListener> mListeners;
    private List<StoreModel> mStores;

    //for volley
    private RequestQueue mRequestQueue;

    /**
     * Hidden class constructor.
     */
    private StoresManager() {
        //Do nothing...
    }

    /**
     * Hidden class constructor.
     */
    private StoresManager(Context context) {
        mContext = context;
        init();
    }

    /**
     * Get the one and only instance of this class.
     * The first calling thread will create an initial instance.
     * This method will only be synchronized on the first call,
     * thus it will not affect speed of our app.
     * @return
     */
    public static StoresManager getInstance(Context context) {
        synchronized(StoresManager.class) {
            if (instance == null) {
                instance = new StoresManager(context);
            }
        }
        return instance;
    }

    /**
     * Initialization of class workers.
     */
    private void init() {
        mListeners = new ArrayList<>(2);
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    /**
     * Release this instance of the utility.
     */
    public void release() {
        mRequestQueue = null;
        mListeners.clear();
        mListeners = null;
        mContext = null;
        instance = null;
    }

    /**
     * Add a listener if it is not already in our list.
     * @param listener Object that implements the IStoresManagerListener interface.
     */
    public void addListener(IStoresManagerListener listener) {
        if(!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    /**
     * Removes a listener from our list.
     * @param listener Listener object to remove. If this value is null, all listeners are removed.
     */
    public void removeListener(IStoresManagerListener listener) {
        if(listener != null) {
            mListeners.remove(listener);
        } else {
            mListeners.clear();
        }
    }

    /**
     * Handle onRequest... for listeners
     * @param passed True if request succeeded and false otherwise.
     */
    private void performOnRequestCompleted(boolean passed, String message) {
        for(IStoresManagerListener listener : mListeners) {
            if(passed) {
                listener.onRequestSuccess();
            } else {
                listener.onRequestFailed(message);
            }
        }
    }

    /**
     * Gets the most recent list of stores from the server.
     * @return Returns a copy of the list of stores.
     */
    public List<StoreModel> getStores() {
        if(mStores != null) {
            return Collections.unmodifiableList(mStores);
        } else {
            return null;
        }
    }

    /**
     * Makes a volley request to get JSON from the default URL.
     * The request success or failure are reported to listeners via the
     * IStoreManagerListener interface.
     */
    public void getStoresFromServer() {
        //TODO: think about separating out volley listeners
        JsonObjectRequest request = new JsonObjectRequest(URL_JSON_STORES, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mStores = StoresJsonParser.parse(response);
                performOnRequestCompleted(true, NULL_STRING);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                performOnRequestCompleted(false, error.getMessage());
            }
        });

        mRequestQueue.add(request);
    }
}
