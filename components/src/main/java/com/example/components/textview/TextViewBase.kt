package com.example.components.textview

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView

abstract class TextViewBase : AppCompatTextView {
    constructor(context: Context, @StyleRes styleRes: Int) : super(
        ContextThemeWrapper(
            context,
            styleRes
        )
    )

    constructor(context: Context, attributeSet: AttributeSet, @StyleRes styleRes: Int) : super(
        ContextThemeWrapper(context, styleRes),
        attributeSet
    )
}