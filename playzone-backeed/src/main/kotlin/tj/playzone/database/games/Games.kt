package tj.playzone.database.games

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Games : Table() {
    private val gameId = Games.varchar(name = "gameId", length = 100)
    private val gameName = Games.varchar(name = "gameName", length = 150)
    private val description = Games.varchar(name = "description", length = 500)
    private var versionGame = Games.varchar(name = "versionGame", length = 20)
    private var sizeGame = Games.varchar(name = "sizeGame", length = 10)
    private var image = Games.varchar(name = "image", length = 200)
    private var logo = Games.varchar(name = "logo", length = 200)
    private var downloadCount = Games.integer(name = "downloadCount")
    private var rateGame = Games.integer(name = "rateGame")

    fun insert(gameDTO: GameDTO): Boolean {
        return try {
            val result = transaction {
                Games.insert {
                    it[gameId] = gameDTO.gameID
                    it[gameName] = gameDTO.gameName
                    it[description] = gameDTO.description
                    it[versionGame] = gameDTO.versionGame
                    it[sizeGame] = gameDTO.sizeGame
                    it[image] = gameDTO.image!!
                    it[logo] = gameDTO.logo!!
                    it[downloadCount] = gameDTO.downloadCount
                    it[rateGame] = gameDTO.rateGame
                }
            }
            result != null
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    fun fetchAll(): List<GameDTO> {
        return try {
            transaction {
                Games.selectAll().toList()
                    .map {
                        GameDTO(
                            gameID = it[gameId],
                            gameName = it[gameName],
                            description = it[description],
                            versionGame = it[versionGame],
                            sizeGame = it[sizeGame],
                            logo=it[logo],
                            image = it[image],
                            rateGame = it[rateGame],
                            downloadCount = it[downloadCount]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}