import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.0"
}

group = "org.kanelucky"
description = "A simple AllayMC plugin that sends a message when a player joins the game"
version = "0.1.1"

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases/")
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
}

val serverVersion: String? = null
dependencies {
    compileOnly(group = "org.allaymc.allay", name = "api", version = "0.23.0")
}

kotlin {
    jvmToolchain(21)
}

tasks.register<JavaExec>("runServer") {
    outputs.upToDateWhen { false }
    dependsOn("shadowJar")

    val shadowJar = tasks.named("shadowJar", ShadowJar::class).get()
    val pluginJar = shadowJar.archiveFile.get().asFile
    val cwd = layout.buildDirectory.file("run").get().asFile
    val pluginsDir = cwd.resolve("plugins").apply { mkdirs() }
    doFirst { pluginJar.copyTo(File(pluginsDir, pluginJar.name), overwrite = true) }

    val group = "org.allaymc.allay"
    val version = serverVersion ?: configurations.compileOnly.get().dependencies
        .find { it.group == group && it.name == "server" }?.version ?: "+"
    val server = dependencies.create("$group:server:${version}")
    classpath = files(configurations.detachedConfiguration(server).resolve())
    mainClass = "org.allaymc.server.Allay"
    workingDir = cwd
}

tasks.named("processResources") {
    fun String.process() =
        replace("\"entrance\": \".", "\"entrance\": \"" + project.group.toString() + ".")
        .replace("\${description}", project.description.toString())
        .replace("\${version}", version.toString())
    doLast {
        val origin = file("src/main/resources/plugin.json")
        val processed = file("${layout.buildDirectory.get()}/resources/main/plugin.json")
        processed.writeText(origin.readText().process())
    }
}
