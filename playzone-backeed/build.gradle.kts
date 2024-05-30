
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "tj.playzone"
version = "0.0.1"

application {
    mainClass.set("tj.playzone.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.ktor:ktor-server-core:1.6.4")
    implementation("io.ktor:ktor-server-cio:1.6.4")

    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    implementation("org.postgresql:postgresql:42.2.2")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    implementation("com.sun.mail:javax.mail:1.6.2")

    implementation("com.google.code.gson:gson:2.8.8")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("com.zaxxer:HikariCP:5.0.1")

}
tasks {
    create("stage").dependsOn("installDist")
}