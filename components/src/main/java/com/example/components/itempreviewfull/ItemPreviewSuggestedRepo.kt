package com.example.components.itempreviewfull

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.example.components.R

class ItemPreviewSuggestedRepo : ItemPreviewFull {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        setWillNotDraw(false)
        binding.apply {
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
            tvSubtitle.setTextColor(ContextCompat.getColor(context, R.color.black_soft))
            tvDescription.updateLayoutParams {
                (this as LayoutParams).topMargin = resources.getDimensionPixelSize(R.dimen.margin_8)
            }
            tvFooter1.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_star_16
                ), null, null, null
            )
        }
    }
}