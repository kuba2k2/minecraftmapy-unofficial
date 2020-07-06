package pl.szczodrzynski.minecraftmapy.ui.map

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import pl.szczodrzynski.minecraftmapy.base.BaseViewModel
import pl.szczodrzynski.minecraftmapy.data.api.model.ApiResponse
import pl.szczodrzynski.minecraftmapy.data.repository.MapRepository
import pl.szczodrzynski.minecraftmapy.data.repository.UserRepository
import pl.szczodrzynski.minecraftmapy.model.MapQuery
import pl.szczodrzynski.minecraftmapy.model.McMap
import pl.szczodrzynski.minecraftmapy.model.User
import pl.szczodrzynski.minecraftmapy.ui.map.comment.MapCommentListPagingSource

class MapViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val map = MutableLiveData<McMap>()

    val user = MutableLiveData<User>()
    val userFetched = MutableLiveData<Boolean>(false)

    val comments = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 10)
    ) {
        MapCommentListPagingSource(mapRepository, map.value?.code)
    }.flow.cachedIn(viewModelScope)

    suspend fun loadMap(map: McMap) {
        fetchUser(map.author.username)
        this.map.postValue(map)
    }

    suspend fun fetchMap(code: String) {

    }

    suspend fun fetchUser(username: String) {
        val response = userRepository.getUser(username)
        if (response is ApiResponse.Success) {
            user.postValue(response.data)
            userFetched.postValue(true)
        }
    }

    fun onCategoryClicked(map: McMap) {
        navigate(MapFragmentDirections.actionToMapListFragment(
            MapQuery(category = map.info.category)
        ))
    }

    fun onVersionClicked(map: McMap) {
        navigate(MapFragmentDirections.actionToMapListFragment(
            MapQuery(version = map.info.version)
        ))
    }

    fun onDownloadsClicked(map: McMap) {

    }

    fun onStarsClicked(map: McMap) {

    }
}
