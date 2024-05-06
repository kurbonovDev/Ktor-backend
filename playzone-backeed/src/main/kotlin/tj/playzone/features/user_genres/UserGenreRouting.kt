package tj.playzone.features.user_genres

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUserGenresRouting() {
    routing() {
        post("add/genres_to_user") {
            val userGenreController = UserGenreController(call)
            userGenreController.chooseGenre()
        }

        post("fetch/user_genres") {
            val userGenreController = UserGenreController(call)
            userGenreController.fetchUserGenres()
        }
    }
}