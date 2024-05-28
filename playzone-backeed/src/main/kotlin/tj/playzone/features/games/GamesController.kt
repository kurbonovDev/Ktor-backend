package tj.playzone.features.games

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.admin_features.genres.Genres
import tj.playzone.database.admin_features.genres.relation_games_genres.GameGenresTable
import tj.playzone.database.games.Games
import tj.playzone.database.games.mapToCreateGameResponse
import tj.playzone.database.games.mapToGameDTO
import tj.playzone.features.games.models.CreateGameRequest
import tj.playzone.features.games.models.FetchGamesRequest
import tj.playzone.utils.TokenCheck
import java.io.File

class GamesController(private val call: ApplicationCall) {

    suspend fun performSearch() {
        val request = call.receive<FetchGamesRequest>()
        val token = call.request.headers["Bearer-Authorization"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokenAdmin(token.orEmpty())) {
            if (request.searchQuery.isBlank()) {
                call.respond(Games.fetchAll())
            } else {
                call.respond(Games.fetchAll().filter { it.gameName.contains(request.searchQuery, ignoreCase = true) })
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun createGame() {
        val token = call.request.headers["Bearer-Authorization"]

        if (TokenCheck.isTokenAdmin(token.orEmpty())) {
            val multipart = call.receiveMultipart()
            var filePathImage: String? = null // Путь к файлу на сервере
            var filePathLogo: String? = null // Путь к файлу на сервере
            var gameRequest: CreateGameRequest? = null
            var listGenre: List<String> = mutableListOf()

            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        if (part.name == "image") {
                            val name = part.originalFileName ?: "unknown"
                            val folderPath = "/images/games"
                            val folderPathLocal = "./images/games"
                            val file = File("$folderPathLocal/$name")
                            filePathImage = "http://127.0.0.1:8080/$folderPath/$name"
                            try {
                                file.parentFile.mkdirs()
                                part.streamProvider().use { input ->
                                    file.outputStream().buffered().use { output ->
                                        input.copyTo(output)
                                    }
                                }
                                println("Файл сохранен: ${file.absolutePath}")
                            } catch (e: Exception) {
                                println("Ошибка при сохранении файла: ${e.message}")
                            }
                        }

                        if (part.name == "logo") {
                            val name = part.originalFileName ?: "unknown"
                            val folderPath = "/images/games"
                            val folderPathLocal = "./images/games"
                            val file = File("$folderPathLocal/$name")
                            filePathLogo = "http://127.0.0.1:8080/$folderPath/$name"
                            try {
                                file.parentFile.mkdirs()
                                part.streamProvider().use { input ->
                                    file.outputStream().buffered().use { output ->
                                        input.copyTo(output)
                                    }
                                }
                                println("Файл сохранен: ${file.absolutePath}")
                            } catch (e: Exception) {
                                println("Ошибка при сохранении файла: ${e.message}")
                            }
                        }

                    }

                    is PartData.FormItem -> {
                        if (part.name == "jsonDataGame") {
                            gameRequest = Gson().fromJson(part.value, CreateGameRequest::class.java)
                        }
                        if (part.name == "jsonDataGenre") {
                            listGenre = Gson().fromJson(part.value, Array<String>::class.java).toList()
                        }

                    }

                    else -> {
                        call.respond(HttpStatusCode.BadRequest, "Что то поошло не так")
                    }
                }
                part.dispose()
            }

            if (filePathImage != null && gameRequest != null && filePathLogo != null && listGenre.isNotEmpty()) {

                val game = gameRequest!!.mapToGameDTO()
                game.image = "$filePathImage"
                game.logo = "$filePathLogo"
                if (Games.insert(game)) {
                    call.respond(game.mapToCreateGameResponse())
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Игра с таким именем уже есть")
                }


                listGenre.forEach {
                    val genre = Genres.selectGenresByName(it)
                    if (genre != null)
                        GameGenresTable.insertGameGenres(gameId = game.gameID, genre.genreId, genre.genreName)
                }


            } else {
                call.respond(HttpStatusCode.BadRequest, "No file provided")
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }


}