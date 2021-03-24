package com.husseinelfeky.moviesexplorer.utils

import android.content.res.AssetManager

fun AssetManager.readAssetFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }
