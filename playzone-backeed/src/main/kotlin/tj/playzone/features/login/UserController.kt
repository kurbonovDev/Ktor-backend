package tj.playzone.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tj.playzone.database.users.GetUser
import tj.playzone.database.users.Users

class UserController(private val call:ApplicationCall) {

    suspend fun getUser(){
        val result = call.receive<GetUser>()
        val userDTO = Users.fetchUser(login = result.login)
        if (userDTO!=null){
            call.respond(HttpStatusCode.OK,userDTO)
        }else{
            call.respond(HttpStatusCode.Unauthorized)
        }

    }
}