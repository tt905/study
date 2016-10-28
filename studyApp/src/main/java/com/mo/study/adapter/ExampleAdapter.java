package com.mo.study.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mo.study.R;

import java.util.List;

/**
 *  Created by motw on 2016/10/28.
 */

public class ExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<String> list;

    public ExampleAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
            return new ExampleViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loadmore_progress, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExampleViewHolder) {
            ExampleViewHolder eHolder = (ExampleViewHolder) holder;
            eHolder.textView.setText(list.get(position));
        }else {
            LoadingViewHolder lHolder = (LoadingViewHolder) holder;
            lHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return list == null? 0: list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null? VIEW_TYPE_LOADING: VIEW_TYPE_ITEM;
    }

    private final static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
}
