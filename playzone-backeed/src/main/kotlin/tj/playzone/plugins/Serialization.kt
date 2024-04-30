package tj.playzone.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()

    }
}

/*
Использование install(ContentNegotiation) с сериализацией позволяет вашему приложению автоматически
 преобразовывать объекты Kotlin в различные форматы контента и обратно. Например, если ваше приложение
  взаимодействует с клиентом, который ожидает данные в формате JSON, благодаря сериализации вы сможете
  легко преобразовывать объекты Kotlin в JSON при отправке ответов клиенту и наоборот, преобразовывать JSON,
   полученный от клиента, в объекты Kotlin для дальнейшей обработки.

Таким образом, использование install(ContentNegotiation) с сериализацией позволяет вашему приложению работать с
данными в различных форматах контента, обеспечивая гибкость и удобство взаимодействия с клиентами и другими сервисами.
*/
