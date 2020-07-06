package pl.szczodrzynski.minecraftmapy.data.api

import pl.szczodrzynski.minecraftmapy.data.api.model.ApiException
import pl.szczodrzynski.minecraftmapy.data.api.model.ApiRawResponse
import pl.szczodrzynski.minecraftmapy.data.api.model.ApiResponse
import retrofit2.Response

fun <T> Response<ApiRawResponse<T>>.toApiResponse(): ApiResponse<T> {
    val response = this

    val body = response.body()
    if (!response.isSuccessful || body?.data == null) {
        val throwable = if (body == null)
            ApiException.nullBody()
        else
            ApiException.create(body.error)

        return ApiResponse.error(throwable)
    }

    return ApiResponse.success(
        data = body.data,
        paging = body.paging
    )
}
