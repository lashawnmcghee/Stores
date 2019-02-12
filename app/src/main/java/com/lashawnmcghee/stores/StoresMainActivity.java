/*
 * Copyright (C) 2018 LLM BottleRocket Store Test
 */
package com.lashawnmcghee.stores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lashawnmcghee.stores.adapters.StoresRecyclerAdapter;
import com.lashawnmcghee.stores.interfaces.IStoresManagerListener;
import com.lashawnmcghee.stores.models.StoreModel;
import com.lashawnmcghee.stores.volley.StoresManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoresMainActivity extends AppCompatActivity implements IStoresManagerListener {
    private static final String TAG = StoresMainActivity.class.getSimpleName();

    private StoresManager mStoreManager;
    private StoresRecyclerAdapter mStoresAdapter;

    @BindView(R.id.tv_error)
    TextView mErrorView;

    @BindView(R.id.rv_stores)
    RecyclerView mStoresView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mSwipeRefresh;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mStoreManager = StoresManager.getInstance(this);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStoreManager.getStoresFromServer();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStoreManager.addListener(this);
        mSwipeRefresh.setRefreshing(true);
        mStoreManager.getStoresFromServer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStoreManager.removeListener(this);
    }


    @Override
    public void onRequestSuccess() {
        mErrorView.setVisibility(View.GONE);
        showExistingStores();
        mSwipeRefresh.setRefreshing(false);
    }

    private Runnable mHideErrorRunnable = new Runnable() {
        @Override
        public void run() {
            mErrorView.setVisibility(View.GONE);
        }
    };

    /**
     * Meant to display any caches store list data even when there are network errors...
     */
    private void showExistingStores() {
        List<StoreModel> stores = mStoreManager.getStores();

        if(stores != null && stores.size() > 0) {
            mErrorView.postDelayed(mHideErrorRunnable, 2000);
            mStoresAdapter = new StoresRecyclerAdapter(this, stores);
            mStoresView.setAdapter(mStoresAdapter);
            mStoresView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onRequestFailed(String message) {
        //TODO: Add detection of difference between local and remote connection issues
        mErrorView.setVisibility(View.VISIBLE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        mSwipeRefresh.setRefreshing(false);
        showExistingStores();
    }
}
