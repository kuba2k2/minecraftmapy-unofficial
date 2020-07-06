package pl.szczodrzynski.minecraftmapy.ui.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import pl.szczodrzynski.minecraftmapy.base.BindingViewHolder
import pl.szczodrzynski.minecraftmapy.databinding.MapListItemBinding
import pl.szczodrzynski.minecraftmapy.model.McMap

class MapListAdapter(
    diffCallback: DiffUtil.ItemCallback<McMap>,
    private val onItemClick: (item: McMap) -> Unit
) : PagingDataAdapter<McMap, BindingViewHolder<MapListItemBinding>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MapListItemBinding> {
        return BindingViewHolder(MapListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<MapListItemBinding>, position: Int) {
        val b = holder.b
        val item = getItem(position)
        b.map = item
        b.root.setOnClickListener { item?.let(onItemClick) }
    }
}
