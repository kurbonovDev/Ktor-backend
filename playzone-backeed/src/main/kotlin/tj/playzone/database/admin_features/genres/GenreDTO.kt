package tj.playzone.database.admin_features.genres

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class GenreDTO(
    val genreId:String,
    val genreName:String
)


