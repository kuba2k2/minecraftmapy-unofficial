package pl.szczodrzynski.minecraftmapy.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import pl.szczodrzynski.minecraftmapy.R
import pl.szczodrzynski.minecraftmapy.base.BindingViewHolder
import pl.szczodrzynski.minecraftmapy.databinding.UserItemBinding
import pl.szczodrzynski.minecraftmapy.drawableTop

class UserAdapter(
    private val viewModel: UserViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<BindingViewHolder<UserItemBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<UserItemBinding> {
        return BindingViewHolder(UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: BindingViewHolder<UserItemBinding>, position: Int) {
        val b = holder.b
        val context = holder.itemView.context
        b.viewModel = viewModel
        b.lifecycleOwner = viewLifecycleOwner

        viewModel.user.observe(viewLifecycleOwner) { user ->
            val config: IconicsDrawable.() -> Unit = {
                sizeDp = 28
                colorInt = 0xff555555.toInt()
            }
            b.starsTotal.drawableTop = IconicsDrawable(context, CommunityMaterial.Icon2.cmd_star_outline).apply(config)
            b.downloadsTotal.drawableTop = IconicsDrawable(context, CommunityMaterial.Icon.cmd_download_outline).apply(config)
            b.mapCount.drawableTop = IconicsDrawable(context, CommunityMaterial.Icon2.cmd_map_outline).apply(config)
            b.commentsWritten.drawableTop = IconicsDrawable(context, CommunityMaterial.Icon.cmd_comment_text_multiple_outline).apply(config)
            b.starsTotal.setOnClickListener { Toast.makeText(context, R.string.user_stars_total_hint, Toast.LENGTH_LONG).show() }
            b.downloadsTotal.setOnClickListener { Toast.makeText(context, R.string.user_downloads_total_hint, Toast.LENGTH_LONG).show() }
            b.mapCount.setOnClickListener { Toast.makeText(context, R.string.user_map_count_hint, Toast.LENGTH_LONG).show() }
            b.commentsWritten.setOnClickListener { Toast.makeText(context, R.string.user_comments_written_hint, Toast.LENGTH_LONG).show() }
        }

        /*viewModel.user.observe(viewLifecycleOwner) { user ->
            b.userImage.load(user.info.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }*/
    }
}
