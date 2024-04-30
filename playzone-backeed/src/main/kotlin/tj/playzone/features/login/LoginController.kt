package tj.playzone.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.tokens.TokenDTO
import tj.playzone.database.tokens.Tokens
import tj.playzone.database.users.Users
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()  //получам от клиента login и password
        val userDTO = Users.fetchUser(receive.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "user not found")
        } else {
            if (userDTO.password == receive.password) {
                val token = UUID.randomUUID().toString()

                Tokens.insert(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(),
                        login = receive.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))

            } else {
                call.respond(HttpStatusCode.BadRequest, "invalid password")
            }
        }
    }
}