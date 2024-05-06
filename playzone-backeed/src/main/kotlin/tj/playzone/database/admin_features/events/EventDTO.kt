package tj.playzone.database.admin_features.events

import kotlinx.serialization.Serializable
import tj.playzone.features.event.models.EventRequest
import tj.playzone.features.event.models.EventResponse
import java.util.UUID

@Serializable
data class EventDTO(
    val eventId:String,
    var gameId:String?,
    val eventName:String,
    val title:String,
    val creatorName:String,
    var imagePreview:String?,
    var imageMain:String?,
    var imageCreator:String?,
    val description:String,
    val status:String,
    val format:String,
    var watcherCount:Int?,
    val link:String,
)


fun EventRequest.mapToEventDTO():EventDTO=
    EventDTO(
        eventId=UUID.randomUUID().toString(),
        gameId=null,
        eventName = eventName,
        imagePreview= null,
        imageMain=null,
        description=description,
        status= status,
        format=format,
        watcherCount=null,
        link=link,
        title = title,
        creatorName = creatorName,
        imageCreator = null
    )


