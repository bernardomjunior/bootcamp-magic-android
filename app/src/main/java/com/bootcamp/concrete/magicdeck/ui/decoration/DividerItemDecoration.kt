package com.bootcamp.concrete.magicdeck.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(
    spaceInDP: Int
) : RecyclerView.ItemDecoration() {

    private val horizontalMargin = spaceInDP/2

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = horizontalMargin
        outRect.right = horizontalMargin
    }
}