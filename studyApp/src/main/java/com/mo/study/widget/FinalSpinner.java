package com.mo.study.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.mo.study.R;
import com.mo.study.adapter.FinalSpinnerAdapter;

/**
 * 模仿一个spinner，原生的太难定制样式了。<br>
 * Created by motw on 2017/3/9.
 */
public class FinalSpinner extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String TAG = FinalSpinner.class.getSimpleName();

    private PopupWindow mPopupWindow;
    private ListView mListView;
    private FinalSpinnerAdapter<String> mAdapter;

    private Drawable arrowDownDrawable; //向上箭头 ->
    private Drawable arrowUpDrawable; //向下箭头 ->

    private OnItemClickListener mListener;
    private int popupWindowMaxHeight;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public FinalSpinner(Context context) {
        this(context, null);
        init(context, null);
    }

    public FinalSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FinalSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        popupWindowMaxHeight = 400; //default

        //初始化下拉列表
        mListView = new ListView(context);
        mListView.setCacheColorHint(0x00000000);
        mListView.setDivider(new ColorDrawable(getResources().getColor(R.color.blue)));
        mListView.setDividerHeight(1);
        mListView.setBackgroundResource(R.drawable.bg_item_spinner);
        mListView.setItemsCanFocus(true);
        mListView.setOnItemClickListener(this);

        //初始化弹出窗口
        mPopupWindow = new PopupWindow(context);
        mPopupWindow.setContentView(mListView);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable( new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDownDrawable, null);
            }
        });

        //初始化右边小箭头
        arrowDownDrawable = getDrawable(context, R.drawable.arrow_down).mutate();
        arrowUpDrawable = getDrawable(context, R.drawable.arrow_up).mutate();

        setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDownDrawable, null);

        setGravity(Gravity.START);
        setOnClickListener(this);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        popupWindowMaxHeight = 5 * h;
        //在这里设置下拉框高宽
        mPopupWindow.setWidth(w);
        mPopupWindow.setHeight(calculatePopupWindowHeight());
//        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        if (mAdapter == null || mAdapter.getCount() <= 0){
            return;
        }

        //显示下拉框
        if (mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }else{
            mPopupWindow.showAsDropDown(this);
            setCompoundDrawablesWithIntrinsicBounds(null, null, arrowUpDrawable, null);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击某一项
        String select = mAdapter.getItem(position);
//        Log.d(TAG,"position: " + position + " value: " + select );
        mPopupWindow.dismiss();
        setText(select);
        if (mListener != null){
            mListener.onItemClick(position);
        }
    }


    public void setAdapter(FinalSpinnerAdapter adapter){
        if (adapter == null){
            return;
        }

        //设置下拉选择框里面的样式和他的parent一样
        adapter.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        adapter.setTextSize(getTextSize());
        adapter.setTextColor(getTextColors());

        mListView.setAdapter(adapter);
        mAdapter = adapter;

        //-- 默认值
        String item0 = mAdapter.getItem(0);
        setText(item0);
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }


    /**
     * Set the maximum height of the dropdown menu.
     *
     * @param height
     *     the height in pixels
     */
    public void setDropdownMaxHeight(int height) {
        popupWindowMaxHeight = height;
        mPopupWindow.setHeight(calculatePopupWindowHeight());
    }


    private int calculatePopupWindowHeight() {
        if (mAdapter == null) {
            return WindowManager.LayoutParams.WRAP_CONTENT;
        }
        float listViewHeight = mAdapter.getCount() * getHeight();

        if (popupWindowMaxHeight > 0 && listViewHeight > popupWindowMaxHeight) {
            return popupWindowMaxHeight;
        } else {
            return (int) listViewHeight;
        }
//        return WindowManager.LayoutParams.WRAP_CONTENT;
    }


    /**
     * Return a drawable object associated with a particular resource ID.
     *
     * <p>Starting in {@link android.os.Build.VERSION_CODES#LOLLIPOP}, the returned drawable will be styled for the
     * specified Context's theme.</p>
     *
     * @param id
     *     The desired resource identifier, as generated by the aapt tool.
     *     This integer encodes the package, type, and resource entry.
     *     The value 0 is an invalid identifier.
     * @return Drawable An object that can be used to draw this resource.
     */
    Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        }
        return context.getResources().getDrawable(id);
    }
}
