package com.study.demolist

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

/**
 * Create by jzhan on 2019/7/5
 * 标题栏
 */
class TitleLayout : FrameLayout {

    private var mContext: Context
    // 标题名称、大小、颜色
    private var titleText: String? = null
    private var titleTextSize: Int = 0
    private var titleTextColor: Int = 0
    // 标题右边文字名称、大小、颜色、右边距
    private var rightText: String? = null
    private var rightTextSize: Int = 0
    private var rightTextColor: Int = 0
    private var rightTextPaddingRight: Int = 0

    // 返回按钮图标、左边距、id
    private var iconBack: Int = 0
    private var iconBackPaddingLeft: Int = 0
    private var iconBackId: Int = 0
    // 事件的监听
    private var listener: OnViewClick? = null


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context
        setBackgroundColor(Color.BLUE)
        init(context, attrs)
        initView()

    }

    private fun init(context: Context, attrs: AttributeSet?) {
        attrs?.run {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout)
            iconBack = attributes.getResourceId(R.styleable.TitleLayout_tl_icon_back, R.drawable.ic_back_black)
            iconBackPaddingLeft = attributes.getDimensionPixelOffset(R.styleable.TitleLayout_tl_icon_back_paddingLeft, dp2px(46f))
            titleText = attributes.getString(R.styleable.TitleLayout_tl_title_text)
            titleTextSize = attributes.getDimensionPixelOffset(R.styleable.TitleLayout_tl_title_textSize, dp2px(34f))
            titleTextColor = attributes.getColor(R.styleable.TitleLayout_tl_title_textColor, Color.BLACK)
            rightText = attributes.getString(R.styleable.TitleLayout_tl_right_text)
            rightTextSize = attributes.getDimensionPixelOffset(R.styleable.TitleLayout_tl_right_textSize, dp2px(30f))
            rightTextColor = attributes.getColor(R.styleable.TitleLayout_tl_right_textColor, Color.BLACK)
            rightTextPaddingRight = attributes.getDimensionPixelOffset(R.styleable.TitleLayout_tl_right_textPaddingRight, dp2px(20f))
            attributes.recycle()
        }
    }

    private fun initView() {
        // 返回按鈕
        val imageView = ImageView(mContext)
        imageView.setImageDrawable(mContext.getDrawable(iconBack))
        imageView.setPadding(iconBackPaddingLeft, paddingTop, paddingRight, paddingBottom)
        val params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        iconBackId = View.generateViewId()
        imageView.id = iconBackId
        params.gravity = Gravity.CENTER_VERTICAL
        imageView.setOnClickListener(this::onClick)
        addView(imageView, params)
        // 标题
        val title = TextView(mContext)
        title.gravity = Gravity.CENTER
        title.text = titleText ?: "标题"
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize.toFloat())
        title.setTextColor(titleTextColor)
        val titleParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        titleParams.gravity = Gravity.CENTER
        addView(title, titleParams)
        // 右边文字
        val rightTextView = TextView(mContext)
        rightTextView.gravity = Gravity.CENTER
        rightTextView.text = rightText ?: "标题"
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize.toFloat())
        rightTextView.setTextColor(rightTextColor)
        rightTextView.setPadding(paddingLeft, paddingTop, rightTextPaddingRight, paddingBottom)
        val rightTextParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        rightTextParams.gravity = Gravity.CENTER_VERTICAL or Gravity.END
        addView(rightTextView, rightTextParams)


    }

    private fun onClick(view: View) {
        when (view.id) {
            iconBackId -> {
                listener?.onIconBackClick()
            }
        }

    }

    fun setIconBack(@DrawableRes iconId: Int) {
        this.iconBack = iconId
    }

    fun setTitleText(titleText: String) {
        this.titleText = titleText
    }

    interface OnViewClick {
        fun onIconBackClick()
    }

    private fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().displayMetrics).toInt()
    }
}