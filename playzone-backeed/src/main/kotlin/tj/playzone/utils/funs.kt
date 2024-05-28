package tj.playzone.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

val objectMapper = jacksonObjectMapper()

fun List<String>.toJson(): String = objectMapper.writeValueAsString(this)

fun String.toListOfStrings(): List<String> = objectMapper.readValue(this)