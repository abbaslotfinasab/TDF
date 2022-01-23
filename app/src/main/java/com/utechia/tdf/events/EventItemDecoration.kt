package com.utechia.tdf.events

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class EventItemDecoration: RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 35
        outRect.right = 35
        outRect.top = 20
        outRect.bottom = 20
    }
}