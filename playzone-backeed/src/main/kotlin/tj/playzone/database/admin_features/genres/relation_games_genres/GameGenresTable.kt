package tj.playzone.database.admin_features.genres.relation_games_genres

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object GameGenresTable : Table() {
    private val game_id = GameGenresTable.varchar("game_id", 100)
    private val genre_id = GameGenresTable.varchar("genre_id", 100)
    private val genre_name = GameGenresTable.varchar("genre_name", 50)

    fun insertGameGenres(gameId: String, genreId: String,genreName:String) {
        try {
            transaction {
                GameGenresTable.insert {
                    it[game_id] = gameId
                    it[genre_id] = genreId
                    it[genre_name] = genreName
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun selectGameGenre(gameId:String):List<String>{
        val list = mutableListOf<String>()
        try {
            transaction {
                GameGenresTable.select { GameGenresTable.game_id eq gameId }.forEach {
                    list.add(it[GameGenresTable.genre_name])
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return list
    }

}