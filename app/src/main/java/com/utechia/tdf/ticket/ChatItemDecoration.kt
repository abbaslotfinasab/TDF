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
        outRect.left = 20
        outRect.right = 20
        outRect.top = 20
        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount-1)
            outRect.bottom = 20
        else
            outRect.bottom = 300    }
}