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
        fetchUser(map.author.username)
    }

    suspend fun fetchMap(code: String) {
        if (this.map.value?.code == code)
            return
        val response = mapRepository.getMap(code)
        if (response is ApiResponse.Success) {
            map.postValue(response.data)
            fetchUser(response.data.author.username)
        }
    }

    suspend fun fetchUser(username: String) {
        if (this.user.value?.info?.username == username)
            return
        val response = userRepository.getUser(username)
        if (response is ApiResponse.Success) {
            user.postValue(response.data)
            userFetched.postValue(true)
        }
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

    fun onDownloadClicked(map: McMap) {

    }

    fun onStarClicked(map: McMap) {

    }
}
