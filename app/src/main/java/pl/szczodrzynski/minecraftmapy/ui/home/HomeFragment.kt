package pl.szczodrzynski.minecraftmapy.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.szczodrzynski.minecraftmapy.base.BaseFragment
import pl.szczodrzynski.minecraftmapy.databinding.HomeFragmentBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>({ inflater, parent ->
    HomeFragmentBinding.inflate(inflater, parent, false)
}) {
    companion object {
        fun newInstance() = HomeFragment()
    }

    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewModel = viewModel
    }
}
