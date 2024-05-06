package tj.playzone.features.event

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.admin_features.events.EventsTable
import tj.playzone.database.admin_features.events.mapToEventDTO
import tj.playzone.database.games.GameDTO
import tj.playzone.database.games.Games
import tj.playzone.features.event.models.EventRequest
import tj.playzone.features.event.models.FetchEventRequest
import tj.playzone.utils.TokenCheck
import java.io.File
import kotlin.random.Random

class EventController(private val call: ApplicationCall) {


    suspend fun performSearch() {
        val request = call.receive<FetchEventRequest>()
        val token = call.request.headers["Bearer-Authorization"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokenAdmin(token.orEmpty())) {
            if (request.searchQuery.isBlank()) {
                call.respond(EventsTable.fetchAll())
            } else {
                call.respond(
                    EventsTable.fetchAll().filter { it.eventName.contains(request.searchQuery, ignoreCase = true) })
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun createEvent() {
        val token = call.request.headers["Bearer-Authorization"]
        val gameName = call.request.headers["Game-Name"]
        if (TokenCheck.isTokenAdmin(token.orEmpty())) {
            val multipart = call.receiveMultipart()
            var pathImagePreview: String? = null // Путь к файлу на сервере
            var pathImageMain: String? = null
            var pathImageCreator: String? = null
            var eventRequest: EventRequest? = null
            var isExist = false
            var game: Pair<GameDTO?, Boolean>? = null

            if (gameName != null) {
                game = Games.getGame(gameName)
                isExist = game.second
            }
            if (isExist) {
                multipart.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            if (part.name == "jsonDataEvent") {
                                eventRequest = Gson().fromJson(part.value, EventRequest::class.java)
                            }
                        }

                        is PartData.FileItem -> {

                            if (part.name == "imagePreview") {
                                val name = part.originalFileName ?: "unknown"
                                val folderPath = "/images/events"
                                val folderPathLocal = "./images/events"
                                val file = File("$folderPathLocal/$name")
                                pathImagePreview = "http://127.0.0.1:8080/$folderPath/$name"
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

                            if (part.name == "imageMain") {
                                val name = part.originalFileName ?: "unknown"
                                val folderPath = "/images/events"
                                val folderPathLocal = "./images/events"
                                val file = File("$folderPathLocal/$name")
                                pathImageMain = "http://127.0.0.1:8080/$folderPath/$name"
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

                            if (part.name == "imageCreator") {
                                val name = part.originalFileName ?: "unknown"
                                val folderPath = "/images/events"
                                val folderPathLocal = "./images/events"
                                val file = File("$folderPathLocal/$name")
                                pathImageCreator = "http://127.0.0.1:8080/$folderPath/$name"
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

                        else -> {
                            call.respond(HttpStatusCode.BadRequest, "Что то пошло не так")
                        }
                    }
                    part.dispose()
                }

            } else {
                call.respond(HttpStatusCode.BadRequest, "Игры нету")
            }

            if (pathImageMain != null && pathImagePreview != null && eventRequest != null && game != null && pathImageCreator != null) {

                if (game.second) {
                    try {
                        val event = eventRequest!!.mapToEventDTO()
                        event.imageMain = pathImageMain
                        event.imagePreview = pathImagePreview
                        event.gameId = game.first?.gameID
                        event.watcherCount = Random.nextInt(1, 1000)
                        event.imageCreator = pathImageCreator
                        EventsTable.insertEvent(event)
                        call.respond(event)
                    } catch (e: Exception) {
                        val file1 = File(pathImageCreator!!)
                        val file3 = File(pathImageMain!!)
                        val file2 = File(pathImagePreview!!)
                        try {
                            if (file1.exists() || file2.exists() || file3.exists()) {
                                if (file1.delete() || file2.delete() || file3.delete()) {
                                    println("Файл удален: ${file1.absolutePath}")
                                    println("Файл удален: ${file2.absolutePath}")
                                    println("Файл удален: ${file3.absolutePath}")
                                } else {
                                    println("Не удалось удалить файл: ${file1.absolutePath}")
                                    println("Не удалось удалить файл: ${file2.absolutePath}")
                                    println("Не удалось удалить файл: ${file3.absolutePath}")
                                }
                            } else {
                                println("Файл не существует: ${file1.absolutePath}")
                                println("Файл не существует: ${file3.absolutePath}")
                                println("Файл не существует: ${file2.absolutePath}")
                            }
                        } catch (e: Exception) {
                            println("Ошибка при удалении файла: ${e.message}")
                        }

                        call.respond(HttpStatusCode.BadRequest, "${e.message}")
                    }

                } else {
                    call.respond(HttpStatusCode.BadRequest, "Такой игры нету в базе и не возможно создать Event")
                }

            } else {
                call.respond(HttpStatusCode.BadRequest, "Какая та ошибка")
            }


        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }


    }
}