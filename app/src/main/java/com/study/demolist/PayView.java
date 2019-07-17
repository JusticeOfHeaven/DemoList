package com.study.demolist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.widget.LinearLayout.HORIZONTAL;

/**
 * Create by jzhan on 2019/7/3
 */
public class PayView extends RelativeLayout {
    private final String TAG = "PayView";
    private final Context mContext;
    // 文本框数量
    private int textViewCount = 6;
    // 文本宽度
    private int textViewWidth;
    // 文本左边距
    private int marginLeft = 5;
    // 文本右边距
    private int marginRight = 5;
    private List<TextView> mList = new ArrayList<>();
    private EditText editText;
    private LinearLayout mContainer;

    public PayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        // 初始化TextView
        editText = new EditText(context);
        addView(editText);
        mContainer = new LinearLayout(context);
        mContainer.setOrientation(HORIZONTAL);
        addView(mContainer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setOnClickListener(this::onClick);

        editText.requestFocus();
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        editText.setKeyListener(DigitsKeyListener.getInstance("123456789"));
        editText.setTextSize(1);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

        editText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                Log.d(TAG, "text = " + text);
                int size = mList.size();
                for (int i = 0; i < size; i++) {
                    if (i <= text.length() - 1) {
                        char charAt = text.charAt(i);
                        mList.get(i).setText(String.valueOf(charAt));
                    } else {
                        mList.get(i).setText("");
                    }
                }
            }
        });
    }

    private void onClick(View view) {
        Log.d(TAG, "弹起键盘");
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }


    private TextView generateTextView(Context context) {
        Log.d(TAG, "generateTextView = " + textViewWidth);
        TextView textView = new TextView(context);
        LayoutParams params = new LayoutParams(textViewWidth, textViewWidth);
        params.setMargins(marginLeft, 0, marginRight, 0);
        textView.setLayoutParams(params);
        textView.setTextSize(20);
        textView.setBackgroundColor(Color.GRAY);
        textView.setText("");
        textView.setTextColor(Color.BLUE);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        textViewWidth = (width / textViewCount) - marginLeft - marginRight;

        addTextView();
    }

    private void addTextView() {
        for (int i = 0; i < textViewCount; i++) {
            TextView textView = generateTextView(mContext);
            mList.add(textView);
            mContainer.addView(textView);
        }
    }

    private class SimpleTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
