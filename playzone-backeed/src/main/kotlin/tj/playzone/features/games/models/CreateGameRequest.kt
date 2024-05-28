package tj.playzone.features.games.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateGameRequest(
    val gameName: String,
    val description: String,
    val versionGame: String,
    val sizeGame: String,
    val downloadCount:Int,
    val rateGame:Int,
)

@Serializable
data class CreateGameResponse(
    val gameID: String,
    val gameName: String,
    val description: String,
    val versionGame: String,
    val sizeGame: String,
    val image:String,
    val logo:String,
    val downloadCount: Int,
    val rateGame: Int,
)