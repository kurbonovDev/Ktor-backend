package tj.playzone.features.forget_password

import io.ktor.server.application.*
import io.ktor.server.routing.*
import tj.playzone.features.register.RegisterController

fun Application.configureForgetPasswordRouting() {
    routing {
        post("/forgetPassword") {
            val forgetPasswordController = ForgetPasswordController(call)
            forgetPasswordController.changePassword()

        }
    }
}