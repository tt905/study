package com.mo.study.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.mo.study.R;

/**
 * loading dialog <br/>
 * Created by motw on 2016/12/23.
 */
public class LoadingDialog extends AlertDialog {


    private TextView text;

    private String message;

    private LoadingDialog(Context context) {
        this(context, R.style.loadingStyle);
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    //几个方便使用的static方法

    public static LoadingDialog show(Context context, String message) {
        return show(context, message, true);
    }

    public static LoadingDialog show(Context context, String message, boolean cancelable) {
        return show(context, message, cancelable, false);
    }

    public static LoadingDialog show(Context context, String message,
                                     boolean cancelable, boolean ousideable) {
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setLoadingText(message);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(ousideable);
        dialog.show();
        return dialog;
    }
    //  end


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setCancelable(true);
//        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading);

        text = (TextView) findViewById(R.id.tv_loading_text);
        if (message != null){
            text.setText(message);
        }
    }

    /**
     * 设置文字
     * @param msg
     */
    public void setLoadingText(String msg){
        if (msg == null){
            return ;
        }

        message = msg;

        if (text != null) {
            text.setText(msg);
        }
    }

}
