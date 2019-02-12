/*
 * Copyright (C) 2018 LLM BottleRocket Store Test
 */
package com.lashawnmcghee.stores.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lashawnmcghee.stores.R;
import com.lashawnmcghee.stores.StoreDetailsActivity;
import com.lashawnmcghee.stores.models.StoreModel;
import com.lashawnmcghee.stores.util.LogTrace;

import java.util.List;

/**
 * Recycler View Adapter for a list of Store models.
 */
public class StoresRecyclerAdapter extends RecyclerView.Adapter<StoresRecyclerAdapter.StoreViewHolder> {
    private static final String TAG = StoresRecyclerAdapter.class.getSimpleName();

    private Context mContext;
    private List<StoreModel> mStoreList;

    /**
     * Upon construction, we need to get an active context and store list.
     * @param context
     * @param storelist
     */
    public StoresRecyclerAdapter(Context context, List<StoreModel> storelist) {
        mContext = context;
        mStoreList = storelist;
    }

    /**
     * This is where we will initially create our view for a selection in the list.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.list_item_store, parent, false);
        StoreViewHolder svh = new StoreViewHolder(view);
        return svh;
    }

    /**
     * When we bind, we will fill in the view with data from our store object.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, final int position) {
        LogTrace.d(TAG, "Adding new view at position: " + position);

        //setup the onClick method
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StoreDetailsActivity.class);
                intent.putExtra("modelIndex", position);
                mContext.startActivity(intent);
            }
        });

        if(mStoreList != null) {
            StoreModel sm = mStoreList.get(position);

            String sPhone = mContext.getString(R.string.phone, sm.getPhone());
            holder.tvPhone.setText(sPhone);
            Linkify.addLinks(holder.tvPhone, Linkify.ALL);
            String sAddress = mContext.getString(R.string.address, sm.getAddress());
            holder.tvAddress.setText(sAddress);

            //Load the image with memory, disk caching
            Glide.with(mContext).load(sm.getStoreLogoURL()).into(holder.ivLogo);
        }
    }

    @Override
    public int getItemCount() {
        if(mStoreList != null) {
            return mStoreList.size();
        } else {
            return 0;
        }
    }

    /**
     * Inner class representing a view holder
     */
    public class StoreViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView  tvPhone;
        TextView  tvAddress;

        public StoreViewHolder(View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iv_logo);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}
