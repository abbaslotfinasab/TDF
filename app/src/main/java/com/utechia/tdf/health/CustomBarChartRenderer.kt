package com.utechia.tdf.health

import android.graphics.Canvas
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler


class CustomBarChartRenderer(
    chart: BarDataProvider?,
    animator: ChartAnimator?,
    viewPortHandler: ViewPortHandler?
) : BarChartRenderer(chart, animator, viewPortHandler) {


    override fun drawValues(c: Canvas?) {
        super.drawValues(c)

    }


    override fun drawValue(c: Canvas?, valueText: String?, x: Float, y: Float, color: Int) {
        super.drawValue(c, valueText, x, y, color)
    }
}