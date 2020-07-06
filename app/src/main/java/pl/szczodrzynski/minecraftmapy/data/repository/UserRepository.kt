package pl.szczodrzynski.minecraftmapy.data.repository

import pl.szczodrzynski.minecraftmapy.data.api.ApiService
import pl.szczodrzynski.minecraftmapy.data.api.toApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getUser(username: String) = api.getUser(username).toApiResponse()
}
