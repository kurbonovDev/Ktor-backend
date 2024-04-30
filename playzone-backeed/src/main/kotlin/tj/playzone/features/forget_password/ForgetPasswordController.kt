package tj.playzone.features.forget_password

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.users.Users
import tj.playzone.features.cache.TemporaryCache
import tj.playzone.features.login.LoginReceiveRemote
import tj.playzone.features.register.EmailService
import tj.playzone.utils.emailForSentMessages
import tj.playzone.utils.passwordForEmailSentMessages

class ForgetPasswordController(private val call: ApplicationCall) {

    private val emailService =
        EmailService("smtp.gmail.com", 587, emailForSentMessages, passwordForEmailSentMessages)

    suspend fun changePassword() {

        val receive = call.receive<ForgetPasswordRemote>()

        val userDTO = Users.fetchUserByEmail(receive.email)

        if (userDTO != null) {
            emailService.sendCheckPasswordForReset(receive.email, "4321")
            call.respond("Check code sent to ${receive.email}")
            TemporaryCache.addData(receive.email, "4321")

        } else {
            call.respond(HttpStatusCode.BadRequest, "Такого email нету в базе")
        }


    }
}