package com.mo.study.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mo.study.R;

import java.util.List;

/**
 *  Created by motw on 2016/10/28.
 */

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<String> list;

    public CardAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
            return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CardViewHolder eHolder = (CardViewHolder) holder;
            eHolder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null? 0: list.size();
    }


    private final static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

}
