package pl.szczodrzynski.minecraftmapy.data.api.model

open class ApiResponse<T> {
    class Error<T>(val throwable: Throwable) : ApiResponse<T>()
    class Success<T>(val data: T, val paging: ApiRawResponse.Paging?) : ApiResponse<T>()

    companion object {
        fun <T> error(throwable: Throwable) = Error<T>(throwable)
        fun <T> success(data: T, paging: ApiRawResponse.Paging?) = Success(data, paging)
    }
}
