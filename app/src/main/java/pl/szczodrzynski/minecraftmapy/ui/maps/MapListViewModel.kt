package pl.szczodrzynski.minecraftmapy.ui.maps

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import pl.szczodrzynski.minecraftmapy.base.BaseViewModel
import pl.szczodrzynski.minecraftmapy.data.repository.MapRepository
import pl.szczodrzynski.minecraftmapy.model.MapQuery
import pl.szczodrzynski.minecraftmapy.model.McMap

class MapListViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var query: MapQuery = MapQuery()
    private var pagingSource: MapListPagingSource? = null
    val flow = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20)
    ) {
        pagingSource = MapListPagingSource(mapRepository, query)
        pagingSource!!
    }.flow.cachedIn(viewModelScope)

    fun updateQuery(mapQuery: MapQuery?) {
        val newQuery = mapQuery ?: MapQuery()
        if (query != newQuery) {
            query = newQuery
            pagingSource?.invalidate()
        }
    }

    fun onMapClicked(map: McMap) {
        navigate(MapListFragmentDirections.actionToMapFragment(map))
    }
}
