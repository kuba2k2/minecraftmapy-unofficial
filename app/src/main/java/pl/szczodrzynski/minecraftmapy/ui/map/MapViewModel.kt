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
import pl.szczodrzynski.minecraftmapy.model.Comment
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
    val mapFetched = MutableLiveData<Boolean>(false)
    val user = MutableLiveData<User>()
    val userFetched = MutableLiveData<Boolean>(false)

    val comments = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20)
    ) {
        MapCommentListPagingSource(mapRepository, map.value?.code)
    }.flow.cachedIn(viewModelScope)

    suspend fun loadMap(map: McMap) {
        if (this.map.value == map)
            return
        this.map.postValue(map)
        mapFetched.postValue(true)
        fetchUser(map.author.username)
    }

    suspend fun loadMap(code: String) {
        if (this.map.value?.code == code)
            return
        fetchMap(code)?.author?.username?.let {
            fetchUser(it)
        }
    }

    private suspend fun fetchMap(code: String): McMap? {
        if (this.map.value?.code == code)
            return null
        val response = mapRepository.getMap(code)
        if (response is ApiResponse.Success) {
            map.postValue(response.data)
            mapFetched.postValue(true)
            return response.data
        }
        return null
    }

    private suspend fun fetchUser(username: String): User? {
        if (this.user.value?.info?.username == username)
            return null
        val response = userRepository.getUser(username)
        if (response is ApiResponse.Success) {
            user.postValue(response.data)
            userFetched.postValue(true)
            return response.data
        }
        return null
    }

    fun onUserClicked(user: User) {
        navigate(MapFragmentDirections.actionToUserFragment(
            user = user,
            username = null
        ))
    }

    fun onCommentClicked(comment: Comment) {
        navigate(MapFragmentDirections.actionToUserFragment(
            user = null,
            username = comment.author.username
        ))
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

    fun onStarClicked(map: McMap) {

    }
}
