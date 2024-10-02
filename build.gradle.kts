repositories {
    mavenLocal()
    mavenCentral()
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("jvm") version libs.versions.kotlin
    `java-library`
    `maven-publish`
}

dependencies {
    implementation(libs.hopliteHocon)
    testImplementation(libs.bundles.kotest)
}

val jvmTargetVersion: String by project

tasks {
    java { toolchain { languageVersion.set(JavaLanguageVersion.of(jvmTargetVersion)) } }

    withType<Test> {
        useJUnitPlatform()
    }
}
