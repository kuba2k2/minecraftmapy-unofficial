package pl.szczodrzynski.minecraftmapy.ui.map.comment

import androidx.paging.PagingSource
import pl.szczodrzynski.minecraftmapy.data.api.model.ApiResponse
import pl.szczodrzynski.minecraftmapy.data.repository.MapRepository
import pl.szczodrzynski.minecraftmapy.model.Comment

class MapCommentListPagingSource constructor(
    private val mapRepository: MapRepository,
    private val mapCode: String?
) : PagingSource<Int, Comment>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
        if (mapCode == null) {
            return LoadResult.Error(Exception("MapCode is not provided."))
        }

        val comments = mapRepository.getMapComments(
                page = params.key ?: 1,
                mapCode = mapCode
        )
        if (comments is ApiResponse.Error) {
            return LoadResult.Error(comments.throwable)
        }
        if (comments !is ApiResponse.Success) {
            return LoadResult.Error(Exception("Invalid ApiResponse type."))
        }
        val paging = comments.paging
                ?: return LoadResult.Error(Exception("Paging is not provided."))

        return LoadResult.Page(
                data = comments.data,
                prevKey = if (paging.currentPage == 1) null else paging.currentPage-1,
                nextKey = if (paging.currentPage == paging.lastPage) null else paging.currentPage+1
        )
    }
}
