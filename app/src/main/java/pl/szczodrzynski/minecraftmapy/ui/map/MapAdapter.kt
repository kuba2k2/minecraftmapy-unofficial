package pl.szczodrzynski.minecraftmapy.ui.map

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import pl.szczodrzynski.minecraftmapy.base.BindingViewHolder
import pl.szczodrzynski.minecraftmapy.databinding.MapItemBinding
import pl.szczodrzynski.minecraftmapy.drawableTop


class MapAdapter(
    private val viewModel: MapViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<BindingViewHolder<MapItemBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MapItemBinding> {
        return BindingViewHolder(MapItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: BindingViewHolder<MapItemBinding>, position: Int) {
        val b = holder.b
        val context = holder.itemView.context
        b.viewModel = viewModel
        b.lifecycleOwner = viewLifecycleOwner

        viewModel.map.observe(viewLifecycleOwner) { map ->
            val config: IconicsDrawable.() -> Unit = {
                sizeDp = 28
                colorInt = 0xff555555.toInt()
            }
            b.categoryName.drawableTop = IconicsDrawable(context, CommunityMaterial.Icon2.cmd_shape_outline).apply(config)
            b.versionName.drawableTop = IconicsDrawable(context, CommunityMaterial.Icon2.cmd_tag_outline).apply(config)
            b.downloadCount.drawableTop = IconicsDrawable(context, CommunityMaterial.Icon.cmd_download_outline).apply(config)
            b.starCount.drawableTop = IconicsDrawable(context, CommunityMaterial.Icon2.cmd_star_outline).apply(config)

            b.downloadCount.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(map.info.downloadUrl)
                context.startActivity(i)
            }
        }
    }
}
