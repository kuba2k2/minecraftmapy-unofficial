package pl.szczodrzynski.minecraftmapy.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MinecraftVersion(
    val id: Int,
    val name: String,
    @SerializedName("is_snapshot")
    val isSnapshot: Boolean,
    @SerializedName("is_bedrock")
    val isBedrock: Boolean,
    @SerializedName("is_other")
    val isOther: Boolean
) : Serializable
