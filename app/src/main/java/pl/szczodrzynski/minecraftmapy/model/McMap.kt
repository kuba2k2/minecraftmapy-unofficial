package pl.szczodrzynski.minecraftmapy.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class McMap(
    val id: Long,
    val code: String,
    val url: String,
    val author: Author,
    val info: Info,
    val images: List<String>,
    val stats: Stats
) : Serializable {
    data class Info(
        val title: String,
        val description: String,
        @SerializedName("download_url")
        val downloadUrl: String,
        @SerializedName("created_at")
        val createdAt: String,
        val category: MapCategory,
        @SerializedName("minecraft_version")
        val version: MinecraftVersion
    ) : Serializable

    data class Stats(
        @SerializedName("download_count")
        val downloadCount: Int,
        @SerializedName("diamond_count")
        val starCount: Int,
        @SerializedName("comment_count")
        val commentCount: Int
    ) : Serializable
}
