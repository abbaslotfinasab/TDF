package com.utechia.tdf.notification

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ItemNotificationDecoration: RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 50
        outRect.right = 50
        outRect.top = 20
        outRect.bottom = 20
    }
}