package tj.playzone.database.admin_features.events.images

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.admin_features.events.images.models.EventImageRequest

class EventImagesController(private val call: ApplicationCall) {

    suspend fun getEventImages() {
        val eventId = call.receive<EventImageRequest>()
        val imageList = EventImages.selectEventImages(eventId = eventId.eventId)
        if (imageList.isNotEmpty())
        call.respond(HttpStatusCode.OK,imageList)
        else {
            call.respond(HttpStatusCode.BadRequest, emptyList<String>())
        }

    }

}