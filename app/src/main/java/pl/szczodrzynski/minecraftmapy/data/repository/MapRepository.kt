package pl.szczodrzynski.minecraftmapy.data.repository

import pl.szczodrzynski.minecraftmapy.data.api.ApiService
import pl.szczodrzynski.minecraftmapy.data.api.model.ApiResponse
import pl.szczodrzynski.minecraftmapy.data.api.toApiResponse
import pl.szczodrzynski.minecraftmapy.model.MapQuery
import pl.szczodrzynski.minecraftmapy.model.McMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getMaps(page: Int = 1, query: MapQuery, recommended: Boolean = false, username: String?): ApiResponse<List<McMap>> {
        if (recommended) {
            return api.getRecommendedMaps(page).toApiResponse()
        }
        if (username != null) {
            return api.getUserMaps(username, page).toApiResponse()
        }
        return api.getMaps(
            page = page,
            sortById = query.sortBy?.id,
            sortSeed = query.sortSeed,
            categoryId = query.category?.id,
            versionId = query.version?.id,
            queryText = query.queryText
        ).toApiResponse()
    }

    suspend fun getMapComments(page: Int = 1, mapCode: String) = api.getMapComments(
            code = mapCode,
            page = page
    ).toApiResponse()
}
