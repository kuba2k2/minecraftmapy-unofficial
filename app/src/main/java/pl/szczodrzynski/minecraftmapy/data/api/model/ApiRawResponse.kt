package pl.szczodrzynski.minecraftmapy.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiRawResponse<T>(
    val data: T?,
    val error: Error?,
    @SerializedName("meta")
    val paging: Paging?
) {
    data class Error(
        val code: String,
        val message: String
    )

    data class Paging(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        @SerializedName("per_page")
        val perPage: Int,
        @SerializedName("total")
        val totalCount: Int
    )
}
