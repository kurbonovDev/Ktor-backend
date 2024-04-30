package tj.playzone.features.register

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureEmailRouting() {

    routing {
        post("/send-otp") {
            val otpController = OtpController(call)
            otpController.checkOtpForRegister()
        }
    }

}

