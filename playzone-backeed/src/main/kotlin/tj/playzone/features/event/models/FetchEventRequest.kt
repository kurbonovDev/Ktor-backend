package tj.playzone.features.event.models

import kotlinx.serialization.Serializable
@Serializable
data class FetchEventRequest(
    val searchQuery: String
)