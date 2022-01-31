package com.utechia.tdf.home.user

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class UserHomeItemDecoration: RecyclerView.ItemDecoration(){


    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {
        outRect.left = 40
        outRect.right = 40
        outRect.bottom = 20
        if (parent.getChildAdapterPosition(view) ==0)
            outRect.top = 40
        else
            outRect.bottom = 20



    }

}