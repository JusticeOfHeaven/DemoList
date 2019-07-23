package com.hello.study_ffmpeg;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import java.io.File;

/**
 * Create by jzhan on 2019/7/16
 */
public class FFmpegMainActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private Player player;
    private static final String[] needPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int RC_LOCATION_CONTACTS_PERM = 124;
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

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void open(View view) {
        if (EasyPermissions.hasPermissions(this, needPermission)) {
            File file = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera/input.mp4");
            Log.d("TAG", "路径 = " + file.getAbsolutePath());
            if (file.exists()) {
                player.start(file.getAbsolutePath());
            } else {
                Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs access to your location and contacts to know where and who you are.",
                    RC_LOCATION_CONTACTS_PERM,
                    needPermission);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
