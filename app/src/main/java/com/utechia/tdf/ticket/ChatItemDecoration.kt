package com.utechia.tdf.ticket

import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.utechia.tdf.R

class ChatItemDecoration: RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        @NonNull outRect: Rect,
        @NonNull view: View,
        @NonNull parent: RecyclerView,
        @NonNull state: RecyclerView.State
    ) {

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = 30
            outRect.right = 30
            outRect.top = 50
            outRect.bottom = 50

        }
        else {
            outRect.left = 16
            outRect.right = 16
            outRect.top = 20
            if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1)
                outRect.bottom = 20
            else
                outRect.bottom = 320
        }
    }
}