package com.utechia.tdf.refreshment

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


class ItemDecorationCart: RecyclerView.ItemDecoration(){


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 35
        outRect.right = 35
        outRect.top = 20

        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount-1)
        outRect.bottom = 20
        else
            outRect.bottom = 230

    }

}