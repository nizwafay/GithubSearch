package com.example.components.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.example.components.databinding.ItemSeparatorBinding

class ItemHorizontalSeparator : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val binding by lazy {
        ItemSeparatorBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    @DimenRes
    var heightDimenRes: Int? = null
        set(value) {
            field = value
            value?.let {
                binding.separatorItem.updateLayoutParams {
                    LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        resources.getDimensionPixelSize(value)
                    )
                }
            }
        }

    @ColorRes
    var colorRes: Int? = null
        set(value) {
            field = value
            value?.let {
                binding.separatorItem.setBackgroundColor(ContextCompat.getColor(context, it))
            }
        }

    @DimenRes
    var marginHorizontal: Int? = null
        set(value) {
            field = value
            binding.separatorItem.updateLayoutParams {
                value?.let {
                    (binding.separatorItem.layoutParams as LayoutParams).apply {
                        setMargins(
                            resources.getDimensionPixelSize(it),
                            0,
                            resources.getDimensionPixelSize(it),
                            0
                        )
                    }
                }
            }
        }

    init {
        setWillNotDraw(false)
    }
}