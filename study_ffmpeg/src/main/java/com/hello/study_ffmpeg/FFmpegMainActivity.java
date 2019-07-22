package com.hello.study_ffmpeg;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Create by jzhan on 2019/7/16
 */
public class FFmpegMainActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private Player player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.surface);
        player = new Player();
        player.setSurefaceView(surfaceView);

    }

    public void open(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera/input.mp4");
        Log.d("TAG","路径 = "+file.getAbsolutePath());
        if (file.exists()) {
            player.start(file.getAbsolutePath());
        }else {
            Toast.makeText(this,"文件不存在",Toast.LENGTH_LONG).show();
        }
    }
}
