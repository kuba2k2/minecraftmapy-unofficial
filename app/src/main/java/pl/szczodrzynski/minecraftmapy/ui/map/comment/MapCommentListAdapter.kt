package pl.szczodrzynski.minecraftmapy.ui.map.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import pl.szczodrzynski.minecraftmapy.base.BindingViewHolder
import pl.szczodrzynski.minecraftmapy.databinding.MapCommentListItemBinding
import pl.szczodrzynski.minecraftmapy.model.Comment

class MapCommentListAdapter(
        diffCallback: DiffUtil.ItemCallback<Comment>,
        private val onItemClick: (item: Comment) -> Unit
) : PagingDataAdapter<Comment, BindingViewHolder<MapCommentListItemBinding>>(diffCallback) {

    var originalPosterUsername: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MapCommentListItemBinding> {
        return BindingViewHolder(MapCommentListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<MapCommentListItemBinding>, position: Int) {
        val b = holder.b
        val item = getItem(position)
        b.comment = item
        b.isOP = item?.author?.username == originalPosterUsername
        b.root.setOnClickListener { item?.let(onItemClick) }
    }
}
