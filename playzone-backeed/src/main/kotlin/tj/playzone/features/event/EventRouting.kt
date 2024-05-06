package tj.playzone.features.event

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import tj.playzone.features.games.GamesController
import java.io.File

fun Application.configureEvents(){
    routing {
        post("/events/add"){
            val eventController = EventController(call)
            eventController.createEvent()
        }

        post("/events/fetch") {
            val eventController = EventController(call)
            eventController.performSearch()
        }

        get("/images/events/{imageName}") {
            val imageName = call.parameters["imageName"]
            if (imageName != null) {
                val imagePath = "./images/events/$imageName"
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


    }
}