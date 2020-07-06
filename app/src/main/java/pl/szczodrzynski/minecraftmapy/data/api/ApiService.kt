package pl.szczodrzynski.minecraftmapy.data.api

import pl.szczodrzynski.minecraftmapy.data.api.model.ApiRawResponse
import pl.szczodrzynski.minecraftmapy.model.Comment
import pl.szczodrzynski.minecraftmapy.model.McMap
import pl.szczodrzynski.minecraftmapy.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recommended")
    suspend fun getRecommendedMaps(
        @Query("page") page: Int? = null
    ): Response<ApiRawResponse<List<McMap>>>

    @GET("map")
    suspend fun getMaps(
        @Query("page") page: Int? = null,
        @Query("sort_by") sortById: Int? = null,
        @Query("s") sortSeed: Int? = null,
        @Query("category_id") categoryId: Int? = null,
        @Query("minecraft_version_id") versionId: Int? = null,
        @Query("query") queryText: String? = null
    ): Response<ApiRawResponse<List<McMap>>>

    @GET("map/{code}")
    suspend fun getMap(
        @Path("code") code: String
    ): Response<ApiRawResponse<McMap>>

    @GET("map/{code}/comments")
    suspend fun getMapComments(
        @Path("code") code: String,
        @Query("page") page: Int? = null
    ): Response<ApiRawResponse<List<Comment>>>

    @GET("user/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<ApiRawResponse<User>>

    @GET("user/{username}/maps")
    suspend fun getUserMaps(
        @Path("username") username: String,
        @Query("page") page: Int? = null
    ): Response<ApiRawResponse<List<McMap>>>
}
