package tj.playzone.database.users

import kotlinx.serialization.Serializable
@Serializable
data class UserDTO(
    val login:String,
    val password:String,
    val email:String,
    val username:String?,
    var userImage:String?
)

@Serializable
data class GetUser(
    val login: String
)