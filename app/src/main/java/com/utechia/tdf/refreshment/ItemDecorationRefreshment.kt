package com.utechia.tdf.refreshment

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationRefreshment: RecyclerView.ItemDecoration(){


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 30
        outRect.right = 30


    }


}