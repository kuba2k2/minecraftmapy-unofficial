package pl.szczodrzynski.minecraftmapy.data.api.model

data class ApiException(
    override val message: String,
    val error: ApiRawResponse.Error?
) : Exception(message) {
    companion object {
        fun create(error: ApiRawResponse.Error?) =
            ApiException("${error?.code}: ${error?.message}", error)

        fun nullBody() =
            ApiException("The returned body is null", null)
    }
}
