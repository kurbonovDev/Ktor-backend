package tj.playzone.features.forget_password

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureConfirmPasswordRouting(){
    routing {
        post("confirmPassword") {
            val confirmNewPassword = ConfirmNewPasswordController(call)
            confirmNewPassword.confirmNewPassword()
        }
    }
}