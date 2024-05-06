package tj.playzone.features.event.models

import kotlinx.serialization.Serializable


@Serializable
data class EventResponse(
    val eventId:String,
    val gameId:String,
    val eventName:String,
    val imagePreview:String,
    val imageMain:String,
    val description:String,
    val status:String,
    val format:String,
    val watcherCount:Int,
    val link:String,
    val imageCreator:String,
    val title:String,
    val creatorName:String
)



@Serializable
data class EventRequest(
    val gameName: String,
    val eventName:String,
    val description: String,
    val status: String,
    val format: String,
    val link: String,
    val title:String,
    val creatorName:String

)