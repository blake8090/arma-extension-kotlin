plugins {
    kotlin("multiplatform") version "1.9.23"
}

group = "bke"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
        // linuxX64("native") { // on Linux
        // macosX64("native") { // on x86_64 macOS
        // macosArm64("native") { // on Apple Silicon macOS
        mingwX64("native") { // on Windows
        binaries {
            sharedLib {
                // baseName = "native" // on Linux and macOS
                baseName = "TestExtension_x64" // on Windows
            }
        }
    }
}

tasks.wrapper {
    gradleVersion = "8.5"
    distributionType = Wrapper.DistributionType.ALL
}
