package com.study.study_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Create by jzhan on 2019/7/3
 */
public class MainRecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "wangyi";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public View onCreateViewHolder(int position, View convertView, ViewGroup parent) {
                convertView=  MainRecyclerViewActivity.this.getLayoutInflater().inflate( R.layout.item_table,parent,false);
                TextView textView= (TextView) convertView.findViewById(R.id.text1);
                textView.setText("网易课堂 "+position);
                Log.i(TAG, "onCreateViewHodler: " + convertView.hashCode());
                return convertView;
            }

            @Override
            public View onBindViewHolder(int position, View convertView, ViewGroup parent) {
                TextView textView= (TextView) convertView.findViewById(R.id.text1);
                textView.setText("网易课堂 "+position);
                Log.i(TAG, "onBinderViewHodler: " + convertView.hashCode());
                return convertView;
            }

            @Override
            public int getItemViewType(int row) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public int getHeight(int index) {
                return 100;
            }
        });
    }
}
