package com.hello.study_ffmpeg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Create by jzhan on 2019/7/16
 */
public class FFmpegMainActivity extends AppCompatActivity {
    private native String stringFromJNI();

    {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.text_view)).setText(stringFromJNI());
    }

    public static void main(String[] args) {

    }

    public int trap(int[] height) {
        int sumTrap = 0;
        int first = height[0];
        int firstIndex = 0;
        int second = 0;
        int secondIndex = 0;
        for (int i = 0; i < height.length; i++) {
            if (height[i] >= first) {
                first = height[i];
                firstIndex = i;

            } else {

            }
        }
        return sumTrap;
    }
}
