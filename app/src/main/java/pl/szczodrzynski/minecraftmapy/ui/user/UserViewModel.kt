package pl.szczodrzynski.minecraftmapy.ui.user

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
import pl.szczodrzynski.minecraftmapy.model.McMap
import pl.szczodrzynski.minecraftmapy.model.User
import pl.szczodrzynski.minecraftmapy.ui.maps.MapListPagingSource

class UserViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val user = MutableLiveData<User>()

    val maps = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20)
    ) {
        MapListPagingSource(mapRepository, username = user.value?.info?.username)
    }.flow.cachedIn(viewModelScope)

    fun loadUser(user: User) {
        if (this.user.value == user)
            return
        this.user.postValue(user)
    }

    suspend fun fetchUser(username: String) {
        if (this.user.value?.info?.username == username)
            return
        val response = userRepository.getUser(username)
        if (response is ApiResponse.Success) {
            user.postValue(response.data)
        }
    }

    fun onMapClicked(map: McMap) {
        navigate(UserFragmentDirections.actionToMapFragment(map))
    }
}
