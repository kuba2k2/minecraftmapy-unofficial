package pl.szczodrzynski.minecraftmapy.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder<B : ViewDataBinding>(val b: B) : RecyclerView.ViewHolder(b.root)
