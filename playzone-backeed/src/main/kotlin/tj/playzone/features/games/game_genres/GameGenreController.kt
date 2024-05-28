package tj.playzone.features.games.game_genres

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import tj.playzone.database.admin_features.genres.relation_games_genres.GameGenresTable

class GameGenreController(private val call: ApplicationCall) {

    suspend fun getGameGenres() {
        val gameId = call.receive<GetGameId>()

        val listGenres = GameGenresTable.selectGameGenre(gameId.gameId)

        call.respond(HttpStatusCode.OK, listGenres)
    }
}

@Serializable
data class GetGameId(
    val gameId: String
)