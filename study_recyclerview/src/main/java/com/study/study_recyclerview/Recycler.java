package com.study.study_recyclerview;

import android.view.View;

import java.util.Stack;

/**
 * Create by jzhan on 2019/7/3
 * 回收池
 * 涉及添加、查找的速率，所以选择栈
 */
public class Recycler {
    private Stack<View>[] views;

    public Recycler(int typeNumber) {
        this.views = new Stack[typeNumber];
        for (int i = 0; i < typeNumber; i++) {
            views[i] = new Stack<View>();
        }
    }

    // 存
    public void put(View view, int type) {
        views[type].push(view);
    }

    // 取
    public View get(int type) {
        try {
            return views[type].pop();
        } catch (Exception e) {
            return null;
        }
    }
}
