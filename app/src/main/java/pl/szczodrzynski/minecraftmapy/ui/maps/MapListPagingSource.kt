package pl.szczodrzynski.minecraftmapy.ui.maps

import androidx.paging.PagingSource
import pl.szczodrzynski.minecraftmapy.data.api.model.ApiResponse
import pl.szczodrzynski.minecraftmapy.data.repository.MapRepository
import pl.szczodrzynski.minecraftmapy.model.MapQuery
import pl.szczodrzynski.minecraftmapy.model.McMap

class MapListPagingSource constructor(
    private val mapRepository: MapRepository,
    query: MapQuery = MapQuery(),
    private val recommended: Boolean = false,
    private val username: String? = null
) : PagingSource<Int, McMap>() {

    var query = query
        set(value) { field = value; invalidate() }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, McMap> {
        val maps = mapRepository.getMaps(
            page = params.key ?: 1,
            query = query,
            recommended = recommended,
            username = username
        )
        if (maps is ApiResponse.Error) {
            return LoadResult.Error(maps.throwable)
        }
        if (maps !is ApiResponse.Success) {
            return LoadResult.Error(Exception("Invalid ApiResponse type."))
        }
        val paging = maps.paging
            ?: return LoadResult.Error(Exception("Paging is not provided."))

        return LoadResult.Page(
            data = maps.data,
            prevKey = if (paging.currentPage == 1) null else paging.currentPage-1,
            nextKey = if (paging.currentPage == paging.lastPage) null else paging.currentPage+1
        )
    }
}
