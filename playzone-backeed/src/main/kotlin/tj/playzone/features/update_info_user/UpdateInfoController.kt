package tj.playzone.features.update_info_user

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.users.UserDTO
import tj.playzone.database.users.Users
import java.io.File

class UpdateInfoController(private val call: ApplicationCall) {

    suspend fun updateUserInfo() {
        //val multiPart = call.receiveMultipart()
        // var pathUserImage:String?=null
        //var userData:UserDTO?=null

        /* multiPart.forEachPart { part->
             when(part){
                 is PartData.FormItem->{
                     if (part.name=="jsonUserData"){
                         userData = Gson().fromJson(part.value, UserDTO::class.java)
                     }
                 }

                 is PartData.FileItem->{
                     if (part.name=="userPhoto"){
                         val name = part.originalFileName ?: "unknown"
                         val folderPath = "/images/users"
                         val folderPathLocal = "./images/users"
                         val file = File("$folderPathLocal/$name")
                         pathUserImage = "http://127.0.0.1:8080/$folderPath/$name"
                         try {
                             file.parentFile.mkdirs()
                             part.streamProvider().use { input ->
                                 file.outputStream().buffered().use { output ->
                                     input.copyTo(output)
                                 }
                             }
                             println("Файл сохранен: ${file.absolutePath}")
                         } catch (e: Exception) {
                             println("Ошибка при сохранении файла: ${e.message}")
                         }
                     }
                 }
                 else -> {
                     call.respond(HttpStatusCode.BadRequest, "Что то поошло не так")
                 }
             }
         }

         if (userData!=null && pathUserImage!=null){
             try {
                 userData?.userImage=pathUserImage
                 Users.updateUser(userData!!)
                 call.respond(HttpStatusCode.OK,"Данные для пользователя ${userData?.login} успешно заменены")

             }catch (e:Exception){
                 call.respond(HttpStatusCode.BadRequest,"Данные для пользователя ${userData?.login} не были изменены")
             }

         }*/

        val userDTO = call.receive<UserDTO>()
        Users.updateUser(userDTO)
        call.respond(HttpStatusCode.OK,"Данные для пользователя ${userDTO.login} успешно заменены")




    }
}