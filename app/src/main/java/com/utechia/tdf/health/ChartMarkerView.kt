package com.utechia.tdf.health

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.utechia.tdf.R


@SuppressLint("ViewConstructor")
class ChartMarkerView constructor(context: Context?, layoutResource: Int,private val axisValueFormatter: ValueFormatter) :
    MarkerView(context, layoutResource) {
    private val tvContent:TextView = findViewById(R.id.markerCount)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        tvContent.text = e?.let { axisValueFormatter.getFormattedValue(it.y) }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width/2)).toFloat(), (-height).toFloat())
    }
}