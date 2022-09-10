package com.example.components.textview.impls

import android.content.Context
import android.util.AttributeSet
import com.example.components.R
import com.example.components.textview.TextViewBase

class TextView10Regular : TextViewBase {
    constructor(context: Context) : super(context, R.style.TextView_10_Regular)
    constructor(context: Context, attributeSet: AttributeSet) : super(
        context,
        attributeSet,
        R.style.TextView_10_Regular
    )
}