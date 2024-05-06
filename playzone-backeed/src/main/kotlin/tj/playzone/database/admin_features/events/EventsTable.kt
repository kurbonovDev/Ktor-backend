package tj.playzone.database.admin_features.events

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import tj.playzone.database.games.GameDTO
import tj.playzone.database.games.Games
import java.util.UUID

object EventsTable:Table() {
    private val eventId = EventsTable.varchar("eventId",100)
    private val eventName = EventsTable.varchar("eventName",100)
    private val gameId = EventsTable.varchar("gameId",100)
    private val imagePreview = EventsTable.varchar("imagePreview",200)
    private val imageMain = EventsTable.varchar("imageMain",200)
    private val description =EventsTable.varchar("description",500)
    private val status = EventsTable.varchar("status",50)
    private val format = EventsTable.varchar("format",50)
    private val watcherCount = EventsTable.integer("watcherCount")
    private val link = EventsTable.varchar("link",200)
    private val title = EventsTable.varchar("title",100)
    private val creatorName = EventsTable.varchar("creatorName",50)
    private val imageCreator = EventsTable.varchar("imageCreator",200)


    fun insertEvent(eventDTO: EventDTO){
        transaction {
            EventsTable.insert {
                it[eventId]= eventDTO.eventId
                it[gameId]=eventDTO.gameId!!
                it[imagePreview]=eventDTO.imagePreview!!
                it[imageMain]=eventDTO.imageMain!!
                it[description]=eventDTO.description
                it[status]=eventDTO.status
                it[format]=eventDTO.format
                it[watcherCount]=eventDTO.watcherCount!!
                it[link]=eventDTO.link
                it[eventName]=eventDTO.eventName
                it[creatorName]=eventDTO.creatorName
                it[imageCreator]=eventDTO.imageCreator!!
                it[title]=eventDTO.title

            }
        }
    }

    fun fetchAll(): List<EventDTO> {
        return try {
            transaction {
                EventsTable.selectAll().toList()
                    .map {
                        EventDTO(
                            eventId = it[EventsTable.eventId],
                            eventName = it[EventsTable.eventName],
                            gameId = it[EventsTable.gameId],
                            imagePreview = it[EventsTable.imagePreview],
                            imageMain = it[EventsTable.imageMain],
                            description = it[EventsTable.description],
                            status = it[EventsTable.status],
                            format = it[EventsTable.format],
                            watcherCount = it[EventsTable.watcherCount],
                            link = it[EventsTable.link],
                            title = it[EventsTable.title],
                            creatorName = it[EventsTable.creatorName],
                            imageCreator = it[EventsTable.imageCreator],
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}