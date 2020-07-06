package pl.szczodrzynski.minecraftmapy.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.szczodrzynski.minecraftmapy.base.BaseFragment
import pl.szczodrzynski.minecraftmapy.databinding.MapFragmentBinding
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

        val mapAdapter = MapAdapter(viewModel, viewLifecycleOwner)
        val commentAdapter = MapCommentListAdapter(MapCommentComparator) {

        }

        b.list.adapter = ConcatAdapter(
            mapAdapter,
            commentAdapter.withLoadStateFooter(
                ListLoadStateAdapter(commentAdapter::retry)
            )
        )
        b.list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        b.list.layoutManager = LinearLayoutManager(context)

        viewModel.map.observe(viewLifecycleOwner) { map ->
            setToolbarExpanded(map.info.title, map.images.first())

            lifecycleScope.launch {
                viewModel.comments.collectLatest { pagingData ->
                    commentAdapter.submitData(pagingData)
                }
            }
        }

        launch {
            viewModel.loadMap(args.map)
        }
    }
}
