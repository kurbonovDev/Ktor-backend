package tj.playzone.features.user_genres

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.admin_features.genres.relation_users_genres.FetchUser
import tj.playzone.database.admin_features.genres.relation_users_genres.UserGenresTable
import tj.playzone.database.admin_features.genres.relation_users_genres.UsersGenresTableDTO
import tj.playzone.database.users.Users

class UserGenreController(private val call: ApplicationCall) {
    suspend fun chooseGenre() {
        val receive = call.receive<UsersGenresTableDTO>()
        val user = Users.fetchUser(receive.userLogin)

        if (user != null && receive.genreId.isNotEmpty()) {
            try {
                UserGenresTable.insertUserGenres(user.login, receive.genreId)
                call.respond(HttpStatusCode.OK, "Жанры успешно добавлены для пользователя ${user.login}")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message!!)
            }

        } else {
            call.respond(HttpStatusCode.BadRequest, "Такого логина нету в базе или список жанров пуст")
        }
    }

    suspend fun fetchUserGenres() {
        val receive = call.receive<FetchUser>()
        if (receive.login.isNotEmpty()) {
            val user = Users.fetchUser(receive.login)

            if (user != null) {
                try {
                    val result = UserGenresTable.selectUserGenres(receive)
                    call.respond(HttpStatusCode.OK,result)

                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest,"Что то пошло не так:${e.message}")
                }
            }
        }

    }
}