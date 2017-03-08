package com.mo.study.ui2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mo.study.R;
import com.mo.study.widget.EditNameDialog;

/**
 * 对话框例子
 * Created by motw on 2017/3/3.
 */
public class DialogActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_content_fg, myDialogFragment);
        ft.commit();
    }

    public static class MyDialogFragment extends Fragment implements EditNameDialog.EditNameDialogListener {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fg_button, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            view.findViewById(R.id.btn_show_dialog2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditDialog();
                }
            });
        }

        // Call this method to launch the edit dialog
        private void showEditDialog() {
            FragmentManager fm = getFragmentManager();
            EditNameDialog editNameDialogFragment = EditNameDialog.newInstance("我的笔筒");
            // SETS the target fragment for use later when sending results
            editNameDialogFragment.setTargetFragment(MyDialogFragment.this, 300);
            editNameDialogFragment.show(fm, "name1");
        }

        @Override
        public void onFinishEditDialog(String inputText) {
            Toast.makeText(getContext(), inputText, Toast.LENGTH_LONG).show();
        }
    }
}
