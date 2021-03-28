package com.husseinelfeky.moviesexplorer.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("app:isVisible")
fun bindVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}
