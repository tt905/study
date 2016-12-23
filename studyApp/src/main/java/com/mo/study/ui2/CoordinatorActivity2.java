package com.mo.study.ui2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mo.study.R;

/**
 * Created by motw on 2016/12/20.
 */
public class CoordinatorActivity2 extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cd2);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.item_simple, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ViewHolder vh = (ViewHolder) holder;
                vh.text.setText("Fake Item " + (position + 1));
                vh.text2.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
            }

            @Override
            public int getItemCount() {
                return 20;
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                TextView text;
                TextView text2;

                public ViewHolder(View itemView) {
                    super(itemView);

                    text = (TextView) itemView.findViewById(R.id.text);
                    text2 = (TextView) itemView.findViewById(R.id.text2);
                }

            }
        });
    }
}
