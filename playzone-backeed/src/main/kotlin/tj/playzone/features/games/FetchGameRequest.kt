package tj.playzone.features.games

import kotlinx.serialization.Serializable

@Serializable
data class FetchGameRequest(
    val token:String
)
