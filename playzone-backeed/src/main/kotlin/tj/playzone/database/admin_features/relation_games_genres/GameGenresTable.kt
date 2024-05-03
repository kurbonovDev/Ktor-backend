package tj.playzone.database.admin_features.relation_games_genres

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object GameGenresTable : Table() {
    private val game_id = GameGenresTable.varchar("game_id", 100)
    private val genre_id = GameGenresTable.varchar("genre_id", 100)

    fun insertGameGenres(gameId: String, genreId: String) {
        try {
            transaction {
                GameGenresTable.insert {
                    it[game_id] = gameId
                    it[genre_id] = genreId
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}