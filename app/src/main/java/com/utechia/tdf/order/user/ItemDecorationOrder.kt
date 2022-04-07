package com.utechia.tdf.order.user

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationOrder: RecyclerView.ItemDecoration(){

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