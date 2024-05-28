package tj.playzone.database.admin_features.events.images

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import tj.playzone.database.admin_features.genres.relation_users_genres.RespondUserGenres
import tj.playzone.database.admin_features.genres.relation_users_genres.UserGenresTable

object EventImages:Table() {
    private var imageId = EventImages.integer("imageId").autoIncrement()
    private var eventId = EventImages.varchar("eventId",50)
    private var image = EventImages.varchar("image",200)


    fun selectEventImages(eventId: String):List<String>{
        val eventImages = mutableListOf<String>()
        transaction {
            EventImages.select {
                EventImages.eventId eq eventId
            }.forEach {
                eventImages.add(it[EventImages.image])
            }
        }
        return eventImages
    }
}
