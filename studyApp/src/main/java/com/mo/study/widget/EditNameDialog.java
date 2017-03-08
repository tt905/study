package com.mo.study.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mo.study.R;

/**
 * 对话框自定义(推荐使用DialogFragment）
 * Created by motw on 2017/3/3.
 */
public class EditNameDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;


    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public EditNameDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialog newInstance(String title) {
        EditNameDialog frag = new EditNameDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        mEditText = (EditText) view.findViewById(R.id.input_name);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "默认标题");
        TextView titleView  = (TextView) view.findViewById(R.id.title_dialog);
        titleView.setText(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mEditText.setOnEditorActionListener(this);
    }

    @Override
    public void onResume() {
        //修改宽width
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            //如果是在Activity里面的时候使用 EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            // Return input text back to activity through the implemented listener
            EditNameDialogListener listener = (EditNameDialogListener) getTargetFragment();
            listener.onFinishEditDialog(mEditText.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }

        return false;
    }
}
