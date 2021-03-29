package com.husseinelfeky.moviesexplorer.utils

import android.content.res.AssetManager

/**
 * Read a file from assets folder.
 *
 * @return the file content as a [String].
 */
fun AssetManager.readAssetFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }
