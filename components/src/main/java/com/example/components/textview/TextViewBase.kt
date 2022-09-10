package com.example.components.textview

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView

abstract class TextViewBase(context: Context, @StyleRes styleRes: Int) :
    AppCompatTextView(ContextThemeWrapper(context, styleRes))