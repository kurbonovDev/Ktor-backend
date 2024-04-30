package tj.playzone.features.forget_password

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.users.Users
import tj.playzone.features.cache.TemporaryCache

class ConfirmNewPasswordController(private val call: ApplicationCall) {

    suspend fun confirmNewPassword(){
        val receive = call.receive<ForgetPasswordRemote>()

        val savedCheckCode = TemporaryCache.getData(receive.email)
        if (receive.checkCode==savedCheckCode){
            Users.changePassword(receive.email,receive.newPassword!!)
            call.respond(HttpStatusCode.OK,"Пароль изменен успешно")
        }else
        {
            call.respond(HttpStatusCode.BadRequest,"Не верный проверичный код")
        }
    }
}