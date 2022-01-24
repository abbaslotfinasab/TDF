package com.utechia.tdf.events

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class GuestItemDecoration: RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 60
        outRect.right = 60
        outRect.bottom = 10
        outRect.top = 10


    }
}


