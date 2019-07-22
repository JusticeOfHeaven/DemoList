package com.hello.study_ffmpeg;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Player implements SurfaceHolder.Callback {

    static {
        System.loadLibrary("wangyiplayer");
    }
    private SurfaceHolder surfaceHolder;
    // 绘制 ndk
    public void setSurefaceView(SurfaceView surefaceView) {
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(this);
        }
        surfaceHolder = surefaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void start(String absolutePath) {
        native_start(absolutePath,surfaceHolder.getSurface());
    }

    public native void native_start(String absolutePath, Surface surface);
}
