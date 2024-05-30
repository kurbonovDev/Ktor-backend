package tj.playzone.database.admin_features.events.images.models

import kotlinx.serialization.Serializable

@Serializable
data class EventImageRequest(
    val eventId:String
)

@Serializable
data class EventImageResponse(
    val eventId:String,
    val eventImages:List<String>
)

