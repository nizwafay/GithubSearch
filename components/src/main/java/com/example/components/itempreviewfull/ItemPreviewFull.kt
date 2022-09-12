package com.example.components.itempreviewfull

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.components.databinding.ItemPreviewFullBinding

open class ItemPreviewFull : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    protected val binding by lazy {
        ItemPreviewFullBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    var photoUrl: String? = null
        set(value) {
            field = value
            Glide.with(this)
                .load(value)
                .circleCrop()
                .into(binding.ivProfilePicture)
        }

    var title: String? = null
        set(value) {
            field = value
            binding.tvTitle.text = value
        }

    var description: String? = null
        set(value) {
            field = value
            binding.tvDescription.text = value
        }

    var footer1Text: String? = null
        set(value) {
            field = value
            binding.tvFooter1.text = value
        }

    var footer2Text: String? = null
        set(value) {
            field = value
            binding.tvFooter2.text = value
        }
}