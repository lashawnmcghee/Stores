/*
 * Copyright (C) 2018 LLM BottleRocket Store Test
 */
package com.lashawnmcghee.stores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lashawnmcghee.stores.interfaces.IGlobalDefines;
import com.lashawnmcghee.stores.models.StoreModel;
import com.lashawnmcghee.stores.volley.StoresManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreDetailsActivity extends AppCompatActivity implements OnMapReadyCallback,
        IGlobalDefines {

    @BindView(R.id.tv_name)
    TextView mNameView;
    @BindView(R.id.tv_phone)
    TextView mPhoneView;
    @BindView(R.id.tv_address)
    TextView mAddressView;
    @BindView(R.id.tv_city)
    TextView mCityView;
    @BindView(R.id.tv_state)
    TextView mStateView;
    @BindView(R.id.tv_zip)
    TextView mZipView;
    @BindView(R.id.tv_lat)
    TextView mLatitudeView;
    @BindView(R.id.tv_long)
    TextView mLongitudeView;
    @BindView(R.id.tv_id)
    TextView mIdView;
    @BindView(R.id.tv_logo)
    TextView mLogoView;
    @BindView(R.id.tv_map_reset)
    TextView mMapResetView;

    //Image and Map
    @BindView(R.id.iv_logo)
    ImageView mLogoImageView;

    private MapView mMapView;
    private GoogleMap mMap;
    private LatLng mLatLng;
    private CameraUpdate mDefaultCamera;

    private StoreModel mStoreModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    private void init(Bundle savedBundle) {
        //pull out the selected store model
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("modelIndex")) {
                int index = bundle.getInt("modelIndex");
                List<StoreModel> stores = StoresManager.getInstance(this).getStores();
                if(stores != null) {
                    mStoreModel = stores.get(index);
                }
            }
        }

        //did we get a model?
        if(mStoreModel == null) {
            finish();
        }

        //fill in text views
        mNameView.setText(getString(R.string.name, mStoreModel.getName()));
        mPhoneView.setText(getString(R.string.phone, mStoreModel.getPhone()));
        Linkify.addLinks(mPhoneView, Linkify.ALL);
        mAddressView.setText(getString(R.string.address, mStoreModel.getAddress()));
        mCityView.setText(getString(R.string.city, mStoreModel.getCity()));
        mStateView.setText(getString(R.string.state, mStoreModel.getState()));
        mZipView.setText(getString(R.string.zipcode, mStoreModel.getZipcode()));
        mLatitudeView.setText(getString(R.string.latitude, mStoreModel.getLatitude()));
        mLongitudeView.setText(getString(R.string.longitude, mStoreModel.getLongitude()));
        mIdView.setText(getString(R.string.storeID, mStoreModel.getStoreID()));
        mLogoView.setText(getString(R.string.storeLogoURL, mStoreModel.getStoreLogoURL()));
        Linkify.addLinks(mLogoView, Linkify.ALL);

        //fill in logo
        Glide.with(this).load(mStoreModel.getStoreLogoURL()).into(mLogoImageView);
        //Picasso.get().load(mStoreModel.getStoreLogoURL()).into(mLogoImageView);

        //set map view
        initMapView(savedBundle);
    }

    private void initMapView(Bundle savedBundle) {
        Bundle mapViewBundle = null;

        if(savedBundle != null) {
            mapViewBundle = savedBundle.getBundle(MAPVIEW_BUNDLE_KEY);
        }


        mMapView = findViewById(R.id.mv_map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        float lat = Float.parseFloat(mStoreModel.getLatitude());
        float lon = Float.parseFloat(mStoreModel.getLongitude());
        mLatLng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(mLatLng).title(mStoreModel.getName()));
        mDefaultCamera = CameraUpdateFactory.newLatLngZoom(mLatLng, 15);
        mMap.animateCamera(mDefaultCamera);

        setupMapReset();
    }

    private void setupMapReset() {
        mMapResetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(mDefaultCamera);
            }
        });
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
