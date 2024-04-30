package tj.playzone.features.forget_password

import kotlinx.serialization.Serializable

@Serializable
data class ForgetPasswordRemote(
    val email:String,
    val checkCode:String?,
    val newPassword: String?
)

/*
@Serializable
data class ForgetPasswordResponse(
    val email:String,
    val newPassword:String
)*/
