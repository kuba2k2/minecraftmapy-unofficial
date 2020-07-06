package pl.szczodrzynski.minecraftmapy.ui.map.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.szczodrzynski.minecraftmapy.databinding.MapCommentListItemBinding
import pl.szczodrzynski.minecraftmapy.model.Comment

class MapCommentListAdapter(
        val context: Context,
        diffCallback: DiffUtil.ItemCallback<Comment>,
        val onItemClick: (item: Comment) -> Unit
) : PagingDataAdapter<Comment, MapCommentListAdapter.MapCommentViewHolder>(diffCallback) {

    class MapCommentViewHolder(val b: MapCommentListItemBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MapCommentViewHolder(MapCommentListItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MapCommentViewHolder, position: Int) {
        val b = holder.b
        val item = getItem(position)
        b.comment = item
        b.root.setOnClickListener { item?.let(onItemClick) }
    }
}
