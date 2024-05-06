package tj.playzone.database.admin_features.genres.relation_users_genres

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tj.playzone.database.admin_features.genres.Genres

object UserGenresTable : Table() {
    private val userLogin = UserGenresTable.varchar("user_login", 100)
    private val genreId = UserGenresTable.varchar("genre_id", 100)
    private val genreName = UserGenresTable.varchar("genre_name", 50)


    fun insertUserGenres(userLogin: String, genreId: List<String>) {
        try {
            transaction {
                genreId.forEach { it ->
                    val genre = Genres.selectGenresByName(it)
                    if (genre != null) {
                        UserGenresTable.insert { table ->
                            table[UserGenresTable.userLogin] = userLogin
                            table[UserGenresTable.genreId] = genre.genreId
                            table[UserGenresTable.genreName] = genre.genreName
                        }
                    }

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun selectUserGenres(userLogin: FetchUser):RespondUserGenres{
        val userGenres = mutableListOf<String>()
        transaction {
            UserGenresTable.select {
                UserGenresTable.userLogin eq userLogin.login
            }.forEach {
                userGenres.add(it[UserGenresTable.genreName])
            }
        }
        return RespondUserGenres(userGenres)
    }

}


@Serializable
data class UsersGenresTableDTO(
    val userLogin: String,
    val genreId: List<String>
)

@Serializable
data class FetchUser(
    val login:String
)

@Serializable
data class RespondUserGenres(
    val userGenres:List<String>
)
