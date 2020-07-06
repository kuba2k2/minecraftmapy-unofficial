package pl.szczodrzynski.minecraftmapy.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import coil.transform.CircleCropTransformation
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.szczodrzynski.minecraftmapy.base.BaseFragment
import pl.szczodrzynski.minecraftmapy.databinding.MapFragmentBinding
import pl.szczodrzynski.minecraftmapy.drawableTop
import pl.szczodrzynski.minecraftmapy.ui.common.ListLoadStateAdapter
import pl.szczodrzynski.minecraftmapy.ui.map.comment.MapCommentComparator
import pl.szczodrzynski.minecraftmapy.ui.map.comment.MapCommentListAdapter

@AndroidEntryPoint
class MapFragment : BaseFragment<MapFragmentBinding>({ inflater, parent ->
    MapFragmentBinding.inflate(inflater, parent, false)
}), CoroutineScope {
    companion object {
        fun newInstance() = MapFragment()
    }

    override val coroutineContext = Job() + Dispatchers.IO

    override val viewModel: MapViewModel by viewModels()
    private val args: MapFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewModel = viewModel

        val adapter = MapCommentListAdapter(view.context, MapCommentComparator) {

        }

        b.comments.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ListLoadStateAdapter(adapter::retry),
                footer = ListLoadStateAdapter(adapter::retry)
        )
        b.comments.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        b.comments.layoutManager = LinearLayoutManager(context)

        viewModel.map.observe(viewLifecycleOwner) { map ->
            val config: IconicsDrawable.() -> Unit = {
                sizeDp = 28
                colorInt = 0xff555555.toInt()
            }
            b.category.drawableTop = IconicsDrawable(view.context, CommunityMaterial.Icon2.cmd_shape_outline).apply(config)
            b.version.drawableTop = IconicsDrawable(view.context, CommunityMaterial.Icon2.cmd_tag_outline).apply(config)
            b.downloads.drawableTop = IconicsDrawable(view.context, CommunityMaterial.Icon.cmd_download_outline).apply(config)
            b.stars.drawableTop = IconicsDrawable(view.context, CommunityMaterial.Icon2.cmd_star_outline).apply(config)
            b.category.setOnClickListener { viewModel.onCategoryClicked(map) }
            b.version.setOnClickListener { viewModel.onVersionClicked(map) }
            b.downloads.setOnClickListener { viewModel.onDownloadsClicked(map) }
            b.stars.setOnClickListener { viewModel.onStarsClicked(map) }
            setToolbarExpanded(map.info.title, map.images.first())

            lifecycleScope.launch {
                viewModel.comments.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
        viewModel.user.observe(viewLifecycleOwner) {
            b.userImage.load(it.info.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

        launch {
            viewModel.loadMap(args.map)
        }
    }
}
