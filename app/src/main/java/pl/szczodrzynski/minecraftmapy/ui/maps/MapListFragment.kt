package pl.szczodrzynski.minecraftmapy.ui.maps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.szczodrzynski.minecraftmapy.R
import pl.szczodrzynski.minecraftmapy.base.BaseFragment
import pl.szczodrzynski.minecraftmapy.databinding.MapListFragmentBinding
import pl.szczodrzynski.minecraftmapy.model.MapQuery
import pl.szczodrzynski.minecraftmapy.ui.common.ListLoadStateAdapter

@AndroidEntryPoint
class MapListFragment : BaseFragment<MapListFragmentBinding>({ inflater, parent ->
    MapListFragmentBinding.inflate(inflater, parent, false)
}) {
    companion object {
        fun newInstance() = MapListFragment()
    }

    override val viewModel: MapListViewModel by viewModels()
    private val args: MapListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarCollapsed(getString(R.string.title_home))
        val adapter = MapListAdapter(view.context, MapComparator) {
            viewModel.onMapClicked(it)
        }

        b.list.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ListLoadStateAdapter(adapter::retry),
                footer = ListLoadStateAdapter(adapter::retry)
        )
        b.list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        b.list.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                b.progressBar.isVisible = loadStates.refresh is LoadState.Loading
            }
        }

        viewModel.pagingSource.query = args.query ?: MapQuery()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}
