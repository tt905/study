package com.mo.study.ui2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mo.study.R;
import com.mo.study.adapter.FinalSpinnerAdapter;
import com.mo.study.widget.FinalSpinner;

import java.util.Arrays;
import java.util.List;

/**
 * spinner study
 * Created by motw on 2017/3/8.
 */
public class SpinnerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        FinalSpinner spinner = (FinalSpinner) findViewById(R.id.spinner_t);
        spinner.setAdapter(new FinalSpinnerAdapter<String>(getData()));
        spinner.setItemClickListener(new FinalSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getApplicationContext(), "position: " + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<String> getData() {
       return Arrays.asList("橘子","BBdk","gdlgd", "gdj454","什么是是非","都没有又所谓","只看到绝色");
    }

}
