package com.utechia.tdf.reservation

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class DateItemDecoration: RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {

        if (parent.getChildAdapterPosition(view) != 0)
            outRect.left = 30
    }

}