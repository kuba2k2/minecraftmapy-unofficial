package pl.szczodrzynski.minecraftmapy.model

import java.io.Serializable

data class MapCategory(
    val id: Int,
    val name: String
) : Serializable
