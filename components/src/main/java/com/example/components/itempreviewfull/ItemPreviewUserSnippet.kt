package com.example.components.itempreviewfull

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.components.R

class ItemPreviewUserSnippet : ItemPreviewFull {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    var subTitle: String? = null
        set(value) {
            field = value
            binding.tvSubtitle.text = value
        }

    init {
        setWillNotDraw(false)
        binding.apply {
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black_medium))
            tvSubtitle.setTextColor(ContextCompat.getColor(context, R.color.black_medium))
        }
    }
}