package com.utechia.tdf.order

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.utechia.tdf.R

class ItemTeaBoyNotificationDecoration: RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 30
        outRect.right = 30
        outRect.top = 10
        outRect.bottom = 10
    }
}