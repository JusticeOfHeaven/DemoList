package com.study.study_svg

import android.graphics.*

/**
 * Create by jzhan on 2019/7/3
 */
class ProvinceItem {

    private var path: Path
    private var drawColor: Int? = null

    constructor(path: Path) {
        this.path = path
    }

    fun setDrawColor(drawColor: Int) {
        this.drawColor = drawColor
    }

    fun drawItem(canvas: Canvas, paint: Paint, isSelect: Boolean) {
        if (isSelect) {
            // 绘制区域颜色
            paint.clearShadowLayer()
            paint.strokeWidth = 1f
            paint.style = Paint.Style.FILL
            paint.color = Color.BLUE
            canvas.drawPath(path, paint)
            // 绘制边界
            paint.style = Paint.Style.STROKE
            val strokeColor = Color.WHITE
            paint.color = strokeColor
            canvas.drawPath(path, paint)
        } else {
            // 绘制区域颜色
            paint.clearShadowLayer()
            paint.strokeWidth = 2f
            paint.style = Paint.Style.FILL
            paint.color = Color.BLUE
            paint.setShadowLayer(8f, 0f, 0f, 0xffffff)
            canvas.drawPath(path, paint)
            // 绘制边界
            paint.clearShadowLayer()
            paint.strokeWidth = 2f
            paint.style = Paint.Style.FILL
            val strokeColor = Color.WHITE
            paint.color = strokeColor
            canvas.drawPath(path, paint)
        }
    }

    /**
     * 通过Region去判断点击事件是否在点击区域
     */
    fun isTouch(x: Float, y: Float): Boolean {
        val rectF = RectF()
        path.computeBounds(rectF, true)
        val region = Region()
        region.setPath(path, Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt()))
        return region.contains(x.toInt(), y.toInt())
    }

}