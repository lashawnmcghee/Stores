/*
 * Copyright (C) 2018 LLM BottleRocket Store Test
 */
package com.lashawnmcghee.stores.models;

import com.lashawnmcghee.stores.util.LogTrace;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing an array of stores from a JSON array.
 * TODO: Consider using GSON or a faster method to generate models rather than get/set(s)
 */
public class StoresJsonParser {
    private static final String TAG = StoresJsonParser.class.getSimpleName();

    public static List<StoreModel> parse(JSONObject jso) {
        List<StoreModel> sm = new ArrayList<>();

        try {
            JSONArray jsa = jso.getJSONArray("stores");
            if(jsa != null) {
                for(int index = 0; index < jsa.length(); index++) {
                    JSONObject jsoStore = jsa.getJSONObject(index);
                    StoreModel model = new StoreModel();
                    model.setAddress(jsoStore.getString("address"));
                    model.setCity(jsoStore.getString("city"));
                    model.setLatitude(jsoStore.getString("latitude"));
                    model.setLongitude(jsoStore.getString("longitude"));
                    model.setName(jsoStore.getString("name"));
                    model.setPhone(jsoStore.getString("phone"));
                    model.setState(jsoStore.getString("state"));
                    model.setStoreID(jsoStore.getString("storeID"));
                    model.setStoreLogoURL(jsoStore.getString("storeLogoURL"));
                    model.setZipcode(jsoStore.getString("zipcode"));

                    LogTrace.d(TAG, "Parsed store: " + model.getName());

                    sm.add(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogTrace.d(TAG, "Parsed stores JSON for entries: " + sm.size());

        return sm;
    }
}
