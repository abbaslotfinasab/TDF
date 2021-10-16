package com.utechia.tdf.utile

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationReservation: RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
            outRect.bottom = 30
            outRect.top = 30


    }
}

class ItemDecorationBooked: RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.bottom = 30
        outRect.left = 30
        outRect.right = 30
        outRect.top = 30


    }
}

