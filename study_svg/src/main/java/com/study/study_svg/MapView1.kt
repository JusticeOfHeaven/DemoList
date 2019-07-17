package com.study.study_svg

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.support.v4.graphics.PathParser
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Create by jzhan on 2019/7/3
 */
class MapView1 : View {
    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private lateinit var mContext: Context
    private var itemList: ArrayList<ProvinceItem> = arrayListOf()
    private val colorArray = intArrayOf(Color.RED, Color.BLUE, -0x7f340f, -0x1)
    private lateinit var paint: Paint
    private var select: ProvinceItem? = null
    private var totalRect: RectF? = null
    private var scale = 0f


    // 初始化

    private fun init(context: Context) {
        this.mContext = context

        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        loadThread.start()
    }

    private val loadThread = object : Thread() {
        override fun run() {
            val inputStream = context.resources.openRawResource(R.raw.china)
            // doom 解析
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc = builder.parse(inputStream)
            val rootElement = doc.documentElement
            val items = rootElement.getElementsByTagName("path")
            var left = -1f
            var right = -1f
            var top = -1f
            var bottom = -1f
            var list: ArrayList<ProvinceItem> = arrayListOf()
            for (i in 0 until items.length) {
                val element = items.item(i) as Element
                val pathData = element.getAttribute("android:pathData")
                @SuppressLint("RestrictedApi")
                val path = PathParser.createPathFromPathData(pathData)
                val provinceItem = ProvinceItem(path)
                provinceItem.setDrawColor(colorArray[i % 4])

                // 得到地图的最小矩形
                val rect = RectF()
                path.computeBounds(rect, true)
                left = if (left == -1f) rect.left else Math.min(left, rect.left)
                right = if (right == -1f) rect.right else Math.max(right, rect.right)
                top = if (top == -1f) rect.top else Math.min(top, rect.top)
                bottom = if (bottom == -1f) rect.bottom else Math.max(bottom, rect.bottom)

                list.add(provinceItem)
            }
            itemList = list

            totalRect = RectF(left, top, right, bottom)
            //刷新界面
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                requestLayout()
                invalidate()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (totalRect != null) {
            val mapWidth = totalRect!!.width()
            scale = width / mapWidth
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (itemList.isNotEmpty()) {
            canvas.save()
            canvas.scale(scale, scale)
            itemList.forEach { provinceItem ->
                if (provinceItem != select) {
                    provinceItem.drawItem(canvas, paint, false)
                } else {
                    provinceItem.drawItem(canvas, paint, true)
                }

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        handleTouch(event.x / scale, event.y / scale)
        return super.onTouchEvent(event)
    }

    private fun handleTouch(x: Float, y: Float) {
        if (itemList.isEmpty()) {
            return
        }
        var selectItem: ProvinceItem? = null
        itemList.forEach { provinceItem: ProvinceItem ->
            if (provinceItem.isTouch(x, y)) {
                selectItem = provinceItem
            }

        }
        if (selectItem != null) {
            select = selectItem
            postInvalidate()
        }
    }
}