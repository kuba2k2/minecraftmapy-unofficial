package pl.szczodrzynski.minecraftmapy.ui.common

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ListLoadStateAdapter(
        private val onRetry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
    ) = LoadStateViewHolder.create(parent, onRetry)

    override fun onBindViewHolder(
            holder: LoadStateViewHolder,
            loadState: LoadState
    ) = holder.bind(loadState)
}
