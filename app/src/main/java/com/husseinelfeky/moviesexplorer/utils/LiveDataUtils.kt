package com.husseinelfeky.moviesexplorer.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.husseinelfeky.moviesexplorer.utils.adapter.DifferentiableItem
import timber.log.Timber

/**
 * Update the recycler view with the live data list items.
 */
fun <T : DifferentiableItem> LiveData<List<T>>.observeChanges(
    viewLifecycleOwner: LifecycleOwner,
    adapter: ListAdapter<DifferentiableItem, RecyclerView.ViewHolder>,
    callback: ((List<T>) -> Unit)? = null
) {
    observe(viewLifecycleOwner) {
        Timber.d("Displayed adapter count: ${it.size}")
        adapter.submitList(it)
        callback?.invoke(it)
    }
}
