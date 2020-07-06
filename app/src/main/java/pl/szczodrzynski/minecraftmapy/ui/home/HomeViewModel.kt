package pl.szczodrzynski.minecraftmapy.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import pl.szczodrzynski.minecraftmapy.base.BaseViewModel

class HomeViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    fun onButtonClick() {
        //navigate()
    }
}
