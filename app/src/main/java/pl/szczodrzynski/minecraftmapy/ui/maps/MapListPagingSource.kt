package pl.szczodrzynski.minecraftmapy.ui.maps

import androidx.paging.PagingSource
import pl.szczodrzynski.minecraftmapy.data.api.model.ApiResponse
import pl.szczodrzynski.minecraftmapy.data.repository.MapRepository
import pl.szczodrzynski.minecraftmapy.model.MapQuery
import pl.szczodrzynski.minecraftmapy.model.McMap
import javax.inject.Inject

class MapListPagingSource @Inject constructor(
    val repository: MapRepository
) : PagingSource<Int, McMap>() {

    var query = MapQuery()
        set(value) { field = value; invalidate() }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, McMap> {
        val maps = repository.getMaps(
            page = params.key ?: 1,
            query = query
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
