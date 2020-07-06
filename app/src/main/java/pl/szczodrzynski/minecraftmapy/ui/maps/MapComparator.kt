package pl.szczodrzynski.minecraftmapy.ui.maps

import androidx.recyclerview.widget.DiffUtil
import pl.szczodrzynski.minecraftmapy.model.McMap

object MapComparator : DiffUtil.ItemCallback<McMap>() {
    override fun areItemsTheSame(oldItem: McMap, newItem: McMap): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: McMap, newItem: McMap): Boolean {
        return oldItem == newItem
    }
}
