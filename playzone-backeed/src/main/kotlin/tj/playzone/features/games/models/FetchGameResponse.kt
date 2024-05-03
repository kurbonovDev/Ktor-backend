package tj.playzone.features.games.models

import kotlinx.serialization.Serializable

@Serializable
data class FetchGamesResponse(
    val games: List<GameResponse>
)

@Serializable
data class GameResponse(
    val gameId: String,
    val title: String,
    val description: String,
    val version: String,
    val size: String,
    val logo:String,
    val image:String,
    val downloadCount:Int,
    val rateGame:Int
)