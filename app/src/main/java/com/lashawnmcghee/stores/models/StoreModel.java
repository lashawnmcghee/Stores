/*
 * Copyright (C) 2018 LLM BottleRocket Store Test
 */
package com.lashawnmcghee.stores.models;

import com.lashawnmcghee.stores.interfaces.IGlobalDefines;

/**
 * Safe STORE model to represent one entry in a list of stores.
 * Every member is a type STRING and never null.
 * If there is no value or the value is never set, it is always NULL_STRING "".
 */
public class StoreModel implements IGlobalDefines {
    private String address;
    private String city;
    private String name;
    private String latitude;
    private String zipcode;
    private String storeLogoURL;
    private String phone;
    private String longitude;
    private String storeID;
    private String state;

    public StoreModel() {
        address = NULL_STRING;
        city = NULL_STRING;
        name = NULL_STRING;
        latitude = NULL_STRING;
        zipcode = NULL_STRING;
        storeLogoURL = NULL_STRING;
        phone = NULL_STRING;
        longitude = NULL_STRING;
        storeID = NULL_STRING;
        state = NULL_STRING;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? NULL_STRING : address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? NULL_STRING : city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? NULL_STRING : name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? NULL_STRING : latitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? NULL_STRING : zipcode;
    }

    public String getStoreLogoURL() {
        return storeLogoURL;
    }

    public void setStoreLogoURL(String storeLogoURL) {
        this.storeLogoURL = storeLogoURL == null ? NULL_STRING : storeLogoURL;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? NULL_STRING : phone;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? NULL_STRING : longitude;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID == null ? NULL_STRING : storeID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? NULL_STRING : state;
    }
}
