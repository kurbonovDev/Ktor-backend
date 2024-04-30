package tj.playzone.database.tokens

import kotlinx.serialization.Serializable


class TokenDTO(
    val rowId:String,
    val login:String,
    val token:String
)