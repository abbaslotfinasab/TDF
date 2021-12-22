package com.utechia.tdf.order

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationOrderDetails: RecyclerView.ItemDecoration(){


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 5
        outRect.right = 5
        outRect.top = 5
        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount-1)
            outRect.bottom = 5
        else
            outRect.bottom = 220


    }

}