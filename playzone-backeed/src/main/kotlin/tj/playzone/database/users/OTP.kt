package tj.playzone.database.users

import kotlinx.serialization.Serializable

@Serializable
data class OTP (
    val email:String,
    val otpCode: String,
    val login:String,
    val password:String,

)