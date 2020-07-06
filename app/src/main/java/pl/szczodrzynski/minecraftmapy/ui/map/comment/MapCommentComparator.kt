package pl.szczodrzynski.minecraftmapy.ui.map.comment

import androidx.recyclerview.widget.DiffUtil
import pl.szczodrzynski.minecraftmapy.model.Comment

object MapCommentComparator : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}
