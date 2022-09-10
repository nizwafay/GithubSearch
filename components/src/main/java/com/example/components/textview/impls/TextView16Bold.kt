package com.example.components.textview.impls

import android.content.Context
import android.util.AttributeSet
import com.example.components.R
import com.example.components.textview.TextViewBase

class TextView16Bold : TextViewBase {
    constructor(context: Context) : super(context, R.style.TextView_16_Bold)
    constructor(context: Context, attributeSet: AttributeSet) : super(
        context,
        attributeSet,
        R.style.TextView_16_Bold
    )
}