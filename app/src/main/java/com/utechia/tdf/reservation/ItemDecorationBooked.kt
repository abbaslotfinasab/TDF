package com.utechia.tdf.reservation

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationBooked: RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        if(parent.getChildAdapterPosition(view)==state.itemCount-1)
            outRect.bottom = 150
        else
            outRect.bottom = 20
        outRect.top = 20
        outRect.left = 30
        outRect.right = 30

    }
}