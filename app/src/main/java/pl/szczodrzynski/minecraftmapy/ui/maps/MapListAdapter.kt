package pl.szczodrzynski.minecraftmapy.ui.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.szczodrzynski.minecraftmapy.databinding.MapListItemBinding
import pl.szczodrzynski.minecraftmapy.model.McMap

class MapListAdapter(
    val context: Context,
    diffCallback: DiffUtil.ItemCallback<McMap>,
    val onItemClick: (item: McMap) -> Unit
) : PagingDataAdapter<McMap, MapListAdapter.FileViewHolder>(diffCallback) {

    class FileViewHolder(val b: MapListItemBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = FileViewHolder(MapListItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val b = holder.b
        val item = getItem(position)
        b.map = item
        b.root.setOnClickListener { item?.let(onItemClick) }
    }
}
