package com.bootcamp.concrete.magicdeck.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(
    private val spaceInDP: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
//        outRect.bottom = spaceInDP;
//        outRect.top = spaceInDP;
        outRect.left = spaceInDP / 2;
        outRect.right = spaceInDP / 2;
    }
}