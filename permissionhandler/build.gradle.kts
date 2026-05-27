import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    id("maven-publish")
}

kotlin {

    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                implementation(libs.kotlinx.coroutines.core)
            }
        }


        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }


        iosMain {
            dependencies { }
        }
    }
    
}

android {
    namespace = "team.platforma.permissionhandler"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

val contributorUserName: String = localProperties.getProperty("USER_NAME", "")
val contributorPassword: String = localProperties.getProperty("PASSWORD", "")
val contributorUserEmail: String = localProperties.getProperty("USER_EMAIL", "")

val libGroupId = "io.github.oooplatform"
val libArtifactId = "permission"
val libVersion = "1.0.0"

version = libVersion

/**
 * ./gradlew publish
 */

publishing {
    publications {
        withType<MavenPublication>().matching { it.name == "kotlinMultiplatform" }.all {
            groupId = libGroupId
            artifactId = libArtifactId
            version = version

            pom {

                name.set("Permission Handler")
                description.set("Kotlin Multiplatform permissions library")
                url.set("https://github.com/oooplatform/runtime_permission_handler")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/license/mit/")
                    }
                }

                developers {
                    developer {
                        id.set("oooplatform")
                        url.set("https://platforma.team/")
                        name.set(contributorUserName)
                        email.set(contributorUserEmail)
                    }
                }

                scm {
                    url.set("https://github.com/oooplatform/runtime_permission_handler")
                }
            }
        }
    }

    repositories {
        val repoDir: String = gradle.startParameter.projectProperties["maven.repo.dir"] ?: "$rootDir/libs/maven-repo"
        maven {
            url = uri(repoDir)
        }
//
//        maven {
//            url = uri("https://nexus.x840.ru/repository/maven-releases/")
//            credentials {
//                username = contributorUserName
//                password = contributorPassword
//            }
//        }
    }
}