package com.mo.study.adapter;

import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mo.study.R;

import java.util.List;

/**
 * Created by motw on 2017/3/9.
 */
public class FinalSpinnerAdapter<T> extends BaseAdapter {


    private List<T> mList;

    private ColorStateList color;
    private float size;
    private int left, top, right, bottom;

    public FinalSpinnerAdapter(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_text, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_tinted_spinner);
            //字体颜色和大小
            if (color != null) {
                holder.textView.setTextColor(color);
            }
            if (size != 0) {
                holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            }
            //内边距
            setTextViewPadding(holder.textView);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(String.valueOf(mList.get(position)));

        return convertView;
    }

    private void setTextViewPadding(TextView textView) {
        if (left == 0){
            left = textView.getPaddingLeft();
        }
        if (top == 0){
            top = textView.getPaddingTop();
        }
        if (right == 0){
            right = textView.getPaddingRight();
        }
        if (bottom == 0){
            bottom = textView.getPaddingBottom();
        }
        textView.setPadding(left, top, right, bottom);
    }

    //---对外暴露的方法

    public void setPadding(int left, int top, int right, int bottom){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void setTextColor(ColorStateList color){
        this.color = color;
    }

    public void setTextSize(float size){
        this.size = size;
    }

    //---End

    private static class ViewHolder {
        public TextView textView;
    }
}
