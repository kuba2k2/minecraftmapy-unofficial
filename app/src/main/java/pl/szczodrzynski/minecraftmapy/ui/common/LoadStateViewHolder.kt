package pl.szczodrzynski.minecraftmapy.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import pl.szczodrzynski.minecraftmapy.databinding.LoadStateItemBinding

class LoadStateViewHolder(
        private val b: LoadStateItemBinding,
        onRetry: () -> Unit
) : RecyclerView.ViewHolder(b.root) {
    companion object {
        fun create(parent: ViewGroup, onRetry: () -> Unit): LoadStateViewHolder {
            val b = LoadStateItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            )
            return LoadStateViewHolder(b, onRetry)
        }
    }

    private val progressBar = b.progressBar
    private val errorText = b.errorText
    private val retryButton = b.retryButton.also {
        it.setOnClickListener { onRetry() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorText.text = loadState.error.localizedMessage
        }

        progressBar.isInvisible = loadState !is LoadState.Loading
        errorText.isInvisible = loadState !is LoadState.Error
        retryButton.isInvisible = loadState !is LoadState.Error
    }
}
