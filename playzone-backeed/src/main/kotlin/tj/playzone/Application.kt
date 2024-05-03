package tj.playzone

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import tj.playzone.features.forget_password.configureForgetPasswordRouting
import tj.playzone.features.forget_password.configureConfirmPasswordRouting
import tj.playzone.features.games.configureGamesRouting
import tj.playzone.features.login.configureLoginRouting
import tj.playzone.features.register.configureEmailRouting
import tj.playzone.features.register.configureRegisterRouting
import tj.playzone.plugins.configureRouting
import tj.playzone.plugins.configureSerialization
import tj.playzone.utils.dbPassword
import tj.playzone.utils.dbUser

fun main() {


    val connect = Database.connect(
        "jdbc:postgresql://localhost:5432/playzone",
        driver = "org.postgresql.Driver",
        password = dbPassword,
        user = dbUser
    )
    embeddedServer(CIO, port = 8080, host = "0.0.0.0"){
        configureRouting()
        configureLoginRouting()
        configureRegisterRouting()
        configureSerialization()
        configureEmailRouting()
        configureForgetPasswordRouting()
        configureConfirmPasswordRouting()
        configureGamesRouting()
    }
        .start(wait = true)
}



