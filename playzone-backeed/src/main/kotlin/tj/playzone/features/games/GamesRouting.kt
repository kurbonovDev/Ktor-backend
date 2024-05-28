package tj.playzone.features.games

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import tj.playzone.features.games.game_genres.GameGenreController
import java.io.File

fun Application.configureGamesRouting() {
    routing {
        post("/games/fetch") {
            val gamesController = GamesController(call)
            gamesController.performSearch()
        }

        post("/games/add") {
            val gamesController = GamesController(call)
            gamesController.createGame()
        }

        get("/images/games/{imageName}") {
            val imageName = call.parameters["imageName"]
            if (imageName != null) {
                val imagePath = "./images/games/$imageName"
                val file = File(imagePath)
                if (file.exists()) {
                    call.respondFile(file)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing image name parameter")
            }
        }

        post ("games/genres"){
            val gamesGenreController = GameGenreController(call)
            gamesGenreController.getGameGenres()
        }


    }
}