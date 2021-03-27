package com.husseinelfeky.moviesexplorer.utils

import android.util.TypedValue
import android.view.View

fun View.dp(value: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        context.resources.displayMetrics
    )
}

fun View.sp(value: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        value,
        context.resources.displayMetrics
    )
}
