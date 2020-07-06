package pl.szczodrzynski.minecraftmapy.ui.user

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
import pl.szczodrzynski.minecraftmapy.databinding.UserFragmentBinding
import pl.szczodrzynski.minecraftmapy.ui.common.ListLoadStateAdapter
import pl.szczodrzynski.minecraftmapy.ui.maps.MapComparator
import pl.szczodrzynski.minecraftmapy.ui.maps.MapListAdapter

@AndroidEntryPoint
class UserFragment : BaseFragment<UserFragmentBinding>({ inflater, parent ->
    UserFragmentBinding.inflate(inflater, parent, false)
}), CoroutineScope {
    companion object {
        fun newInstance() = UserFragment()
    }

    override val coroutineContext = Job() + Dispatchers.IO

    override val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarCollapsed(args.user?.info?.username ?: args.username ?: "")

        val userAdapter = UserAdapter(viewModel, viewLifecycleOwner)
        val mapAdapter = MapListAdapter(MapComparator) {
            viewModel.onMapClicked(it)
        }

        b.list.adapter = ConcatAdapter(
            userAdapter,
            mapAdapter.withLoadStateFooter(
                ListLoadStateAdapter(mapAdapter::retry)
            )
        )
        b.list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        b.list.layoutManager = LinearLayoutManager(context)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            setToolbarCollapsed(user.info.username)

            lifecycleScope.launch {
                viewModel.maps.collectLatest { pagingData ->
                    mapAdapter.submitData(pagingData)
                }
            }
        }

        launch {
            if (args.user != null)
                viewModel.loadUser(args.user!!)
            else if (args.username != null)
                viewModel.fetchUser(args.username!!)
            //else
            //    throw Exception("No user or username supplied.")
        }
    }
}
