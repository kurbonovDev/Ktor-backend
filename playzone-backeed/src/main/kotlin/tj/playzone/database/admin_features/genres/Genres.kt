package tj.playzone.database.admin_features.genres

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object Genres : Table() {
    private val genreId = Genres.varchar("genreId", 100)
    private val genreName = Genres.varchar("genreName", 50)


    private fun insertGenres(genreDTO: GenreDTO) {
        transaction {
            Genres.insert {
                it[genreId] = genreDTO.genreId
                it[genreName] = genreDTO.genreName
            }
        }
    }

    fun selectGenresByName(genreName: String): GenreDTO? {
        return try {
            transaction {
                val result = Genres.select { Genres.genreName eq genreName }.singleOrNull()

                if (result != null) {
                    GenreDTO(result[Genres.genreId], result[Genres.genreName])
                } else {
                    val genreId = UUID.randomUUID().toString()
                    insertGenres(GenreDTO(genreId = genreId,genreName=genreName))
                    GenreDTO(genreId, genreName)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            null
        }
    }
}


