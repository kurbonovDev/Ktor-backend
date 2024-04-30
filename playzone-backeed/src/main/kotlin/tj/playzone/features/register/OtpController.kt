package tj.playzone.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import tj.playzone.database.tokens.TokenDTO
import tj.playzone.database.tokens.Tokens
import tj.playzone.database.users.OTP
import tj.playzone.database.users.UserDTO
import tj.playzone.database.users.Users
import tj.playzone.features.cache.TemporaryCache
import java.util.*

class OtpController(private val call: ApplicationCall) {

    suspend fun checkOtpForRegister() {
        val otpReceive = call.receive<OTP>()

        val savedOtp = TemporaryCache.getData(otpReceive.email)

        if (otpReceive.otpCode == savedOtp) {
            val userDTO = Users.fetchUser(otpReceive.login)

            if (userDTO != null) {
                call.respond(HttpStatusCode.Conflict, "User is already exist")
            } else {
                val token = UUID.randomUUID().toString()

                try {
                    Users.insert(
                        UserDTO(
                            login = otpReceive.login,
                            password = otpReceive.password,
                            email = otpReceive.email,
                            username = ""
                        )
                    )


                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.Conflict, "User is already exist")
                }




                Tokens.insert(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(),
                        login = otpReceive.login,
                        token = token
                    )
                )
                call.respond(RegisterResponseRemote(token = token))
                TemporaryCache.removeData(otpReceive.email)
            }

        }else{
            call.respond(HttpStatusCode.BadRequest,"Otp is not same")
        }

    }
}