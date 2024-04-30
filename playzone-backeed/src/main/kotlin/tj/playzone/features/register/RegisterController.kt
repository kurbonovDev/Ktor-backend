package tj.playzone.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import tj.playzone.database.tokens.TokenDTO
import tj.playzone.database.tokens.Tokens
import tj.playzone.database.users.UserDTO
import tj.playzone.database.users.Users
import tj.playzone.features.cache.TemporaryCache
import tj.playzone.utils.emailForSentMessages
import tj.playzone.utils.isValidEmail
import tj.playzone.utils.passwordForEmailSentMessages
import java.util.*

class RegisterController(private val call: ApplicationCall) {

   private val emailService = EmailService("smtp.gmail.com", 587, emailForSentMessages, passwordForEmailSentMessages)


    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        val userDTO = Users.fetchUser(login = registerReceiveRemote.login, email = registerReceiveRemote.email)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User is already exist")
        }else{

        val otpCode ="1234"
        emailService.sendOtpEmail(registerReceiveRemote.email, otpCode)
        call.respond("OTP code sent to ${registerReceiveRemote.email}")
        TemporaryCache.addData(registerReceiveRemote.email,otpCode)

        }


      /*  if (registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")  //если его нету то отпряем BadRequest
        }
        val userDTO = Users.fetchUser(registerReceiveRemote.login)


        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User is already exist")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        login = registerReceiveRemote.login,
                        password = registerReceiveRemote.password,
                        email = registerReceiveRemote.email,
                        username = ""
                    )
                )


            }catch (e:ExposedSQLException){
                call.respond(HttpStatusCode.Conflict, "User is already exist")
            }




            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(),
                    login = registerReceiveRemote.login,
                    token = token
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }*/


    }
}

