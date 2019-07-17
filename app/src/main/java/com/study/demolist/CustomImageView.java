package com.study.demolist;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Create by jzhan on 2019/7/4
 * 可平移、旋转、缩放的ImageView
 */
public class CustomImageView extends AppCompatImageView {
    private final String TAG = "CustomImageView";
    private float downX;
    private float downY;
    private int touchSlop;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();
//        ImageView imageView = new ImageView(context);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX();
                float dy = event.getY();

                int x = (int) (dx - downX);
                int y = (int) (dy - downY);
                if (Math.sqrt(x * x + y * y) < touchSlop) {
                    return true;
                }
                scrollBy(x, y);
                Log.d(TAG, "dx = " + dx + "  dy = " + dy + "  x = " + x + "   y = " + y);

                downX = dx;
                downY = dy;
                break;
        }
        return true;
    }

    @Override
    public void scrollBy(int x, int y) {
        layout(getLeft() + x,getTop() + y,getRight() + x,getBottom() + y);
    }
}
