package tj.playzone.features.update_info_user

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUpdateInfoRouting() {
    routing {

        post("update_user_info") {
            val updateInfoController = UpdateInfoController(call)
            updateInfoController.updateUserInfo()
        }
    }

}