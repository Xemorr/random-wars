plugins {
    java
    `kotlin-dsl`
    id("com.github.johnrengelman.shadow") version("7.1.2")
}

group = "me.xemor"
version = "1.2.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven{ url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.shadowJar {
    minimize()
    configurations = listOf(project.configurations.shadow.get())
    var folder = System.getenv("pluginFolder")
    if (folder == null) folder = "D:\\"
    destinationDirectory.set(file(folder))
}

tasks.processResources {
    expand(project.properties)
}