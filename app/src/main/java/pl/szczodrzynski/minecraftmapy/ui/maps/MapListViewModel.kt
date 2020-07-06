package pl.szczodrzynski.minecraftmapy.ui.maps

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import pl.szczodrzynski.minecraftmapy.base.BaseViewModel
import pl.szczodrzynski.minecraftmapy.model.McMap

class MapListViewModel @ViewModelInject constructor(
    val pagingSource: MapListPagingSource,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val flow = Pager(
        PagingConfig(pageSize = 10, initialLoadSize = 5)
    ) {
        pagingSource
    }.flow.cachedIn(viewModelScope)

    init {
        /*viewModelScope.launch {
            _files.postValue(Result.loading())
            _files.postValue(repository.getFiles())
        }*/
    }

    fun onMapClicked(map: McMap) {
        navigate(MapListFragmentDirections.actionToMapFragment(map))
    }
}
