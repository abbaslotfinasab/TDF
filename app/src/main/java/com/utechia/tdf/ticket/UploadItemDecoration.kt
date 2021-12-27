package com.utechia.tdf.ticket

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


class UploadItemDecoration: RecyclerView.ItemDecoration(){


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 50
        outRect.right = 50
        outRect.top = 5
        outRect.bottom = 5


    }

}