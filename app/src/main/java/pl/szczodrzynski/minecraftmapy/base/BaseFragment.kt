package pl.szczodrzynski.minecraftmapy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import pl.szczodrzynski.minecraftmapy.MainViewModel

abstract class BaseFragment<B : ViewDataBinding>(
    private val inflater: (inflater: LayoutInflater, parent: ViewGroup?) -> B
) : Fragment() {

    protected lateinit var b: B
    protected abstract val viewModel: BaseViewModel
    protected val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = this.inflater(inflater, container)
        b.lifecycleOwner = viewLifecycleOwner
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.navCommand.observe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }
    }

    protected fun setToolbarCollapsed(title: CharSequence) {
        activityViewModel.toolbarTitle.postValue(title)
        activityViewModel.toolbarExpandableImage.postValue(null)
    }

    protected fun setToolbarExpanded(title: CharSequence, imageUrl: String) {
        activityViewModel.toolbarTitle.postValue(title)
        activityViewModel.toolbarExpandableImage.postValue(imageUrl)
    }
}
