package pl.szczodrzynski.minecraftmapy

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel @ViewModelInject constructor(

) : ViewModel() {
    val toolbarTitle = MutableLiveData<CharSequence>()
    val toolbarExpandableImage = MutableLiveData<String?>()
}
