/*
 * Copyright (C) 2018 LLM BottleRocket Store Test
 */
package com.lashawnmcghee.stores.interfaces;

public interface IStoresManagerListener {
    void onRequestSuccess();
    void onRequestFailed(String message);
}
