package tj.playzone.database.games

import kotlinx.serialization.Serializable
import tj.playzone.features.games.models.CreateGameRequest
import tj.playzone.features.games.models.CreateGameResponse
import java.util.*

@Serializable
data class GameDTO(
    val gameID: String,
    val gameName: String,
    val description: String,
    val versionGame: String,
    val sizeGame: String,
    var image:String?,
    var logo:String?,
    val downloadCount:Int,
    val rateGame:Int

)

fun CreateGameRequest.mapToGameDTO(): GameDTO =
    GameDTO(
        gameID = UUID.randomUUID().toString(),
        gameName = gameName,
        description = description,
        versionGame = versionGame,
        sizeGame = sizeGame,
        image = null,
        logo=null,
        downloadCount = downloadCount,
        rateGame=rateGame

    )

fun GameDTO.mapToCreateGameResponse(): CreateGameResponse =
    CreateGameResponse(
        gameID = gameID,
        gameName = gameName,
        description = description,
        versionGame = versionGame,
        sizeGame = sizeGame,
        image = image!!,
        logo= logo!!,
        rateGame=rateGame,
        downloadCount = downloadCount


    )
