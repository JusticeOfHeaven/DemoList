package com.study.study_ndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Create by jzhan on 2019/7/10
 */
public class NdkMainActivity extends AppCompatActivity {
    {
        System.loadLibrary("hello-jni");
    }
    native int nativeTest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_msg = findViewById(R.id.tv_msg);
        tv_msg.setText("nativeTest："+nativeTest());
    }
}
