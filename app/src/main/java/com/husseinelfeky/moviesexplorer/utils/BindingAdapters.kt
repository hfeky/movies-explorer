package com.husseinelfeky.moviesexplorer.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.husseinelfeky.moviesexplorer.R

@BindingAdapter("app:isVisible")
fun bindVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("app:imageUrl")
fun bindUrl(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(imageView)
            .load(imageUrl)
            .error(R.drawable.img_error_state)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}
