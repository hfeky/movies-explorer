package com.husseinelfeky.moviesexplorer.utils

import android.app.Activity
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackbar(message: String, @Duration duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(findViewById(android.R.id.content), message, duration).show()
}
