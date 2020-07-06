package pl.szczodrzynski.minecraftmapy.model

import pl.szczodrzynski.minecraftmapy.model.enum.SortMode
import java.io.Serializable

data class MapQuery(
    val sortBy: SortMode? = null,
    val sortSeed: Int? = null,
    val category: MapCategory? = null,
    val version: MinecraftVersion? = null,
    val queryText: String? = null
) : Serializable
