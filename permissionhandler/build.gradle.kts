import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    id("maven-publish")
    id("signing")

    // (ОПЦИОНАЛЬНО, но рекомендуется)
    // id("org.jetbrains.dokka") version "1.9.20"
}

group = "io.github.smirnovdv-98"
version = "1.0.0"

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

        iosMain {}
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

publishing {

    publications {
//        withType<MavenPublication>().configureEach {
//            signing.sign(this)
//        }
        withType<MavenPublication>().configureEach {

            // ====================================================
            // CHANGE #1: НЕ трогаем artifactId вручную
            // ====================================================
            // Kotlin MPP сам создаёт корректные artifactId
            // (android, ios, metadata)
            //
            // ❌ УБРАНО:
            // artifactId = "..."

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

                        // CHANGE #2:
                        // оставляем реальные значения только через properties
                        name.set(project.findProperty("USER_NAME") as String? ?: "")
                        email.set(project.findProperty("USER_EMAIL") as String? ?: "")
                    }
                }

                scm {
                    connection.set(
                        "scm:git:git://github.com/oooplatform/runtime_permission_handler.git"
                    )
                    developerConnection.set(
                        "scm:git:ssh://github.com/oooplatform/runtime_permission_handler.git"
                    )
                    url.set("https://github.com/oooplatform/runtime_permission_handler")
                }
            }
        }
    }

    repositories {
        maven {
            name = "local"
            url = uri("$rootDir/libs/maven-repo")
        }
    }
}
signing {
    useGpgCmd()
}
afterEvaluate {
    publishing.publications.withType<MavenPublication>().all {
        signing.sign(this)
    }
}
//signing {
//
//    useGpgCmd()
//    sign(publishing.publications)
////
////    /**
////     * Reads signing keys from gradle.properties
////     */
////    useInMemoryPgpKeys(
////        providers.gradleProperty("signing.keyId").orNull,
////        providers.gradleProperty("signing.key").orNull,
////        providers.gradleProperty("signing.password").orNull
////    )
////
////    /**
////     * Signs ALL publications.
////     */
////    sign(publishing.publications)
//}
//import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
//import org.jetbrains.kotlin.gradle.dsl.JvmTarget
//import java.io.FileInputStream
//import java.util.Properties
//import kotlin.apply
//
//plugins {
//    alias(libs.plugins.kotlinMultiplatform)
//    alias(libs.plugins.androidLibrary)
//
//    id("maven-publish")
//    id("signing")
//    id("org.jetbrains.dokka")
//}
//
//kotlin {
//
//    androidTarget {
//        publishLibraryVariants("release")
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_11)
//        }
//    }
//
//    iosArm64()
//    iosSimulatorArm64()
//
//    sourceSets {
//        commonMain {
//            dependencies {
//                implementation(libs.kotlin.stdlib)
//
//                implementation(libs.kotlinx.coroutines.core)
//            }
//        }
//
//
//        androidMain {
//            dependencies {
//                implementation(libs.androidx.activity.compose)
//            }
//        }
//
//
//        iosMain {
//            dependencies { }
//        }
//    }
//
//}
//
//android {
//    namespace = "team.platforma.permissionhandler"
//    compileSdk = libs.versions.android.compileSdk.get().toInt()
//    defaultConfig {
//        minSdk = libs.versions.android.minSdk.get().toInt()
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//}
//
//val localProperties = Properties().apply {
//    val localPropertiesFile = rootProject.file("local.properties")
//    if (localPropertiesFile.exists()) {
//        load(FileInputStream(localPropertiesFile))
//    }
//}
//
//val contributorUserName: String = localProperties.getProperty("USER_NAME", "")
//val contributorPassword: String = localProperties.getProperty("PASSWORD", "")
//val contributorUserEmail: String = localProperties.getProperty("USER_EMAIL", "")
//
//val libGroupId = "io.github.smirnovdv-98"
//val libArtifactId = "permission-test"
//val libVersion = "1.0.0"
//
//version = libVersion
//
///**
// * ./gradlew publish
// */
//
//publishing {
//    publications {
//        withType<MavenPublication>().matching { it.name == "kotlinMultiplatform" }.all {
//            groupId = libGroupId
////            artifactId = libArtifactId
//            version = version
//
//            pom {
//
//                name.set("Permission Handler")
//                description.set("Kotlin Multiplatform permissions library")
//                url.set("https://github.com/oooplatform/runtime_permission_handler")
//
//                licenses {
//                    license {
//                        name.set("MIT License")
//                        url.set("https://opensource.org/license/mit/")
//                    }
//                }
//
//                developers {
//                    developer {
//                        id.set("smirnovdv-98")
//                        url.set("https://platforma.team/")
//                        name.set(contributorUserName)
//                        email.set(contributorUserEmail)
//                    }
//                }
//
//                scm {
//                    connection.set(
//                        "scm:git:git://github.com/oooplatform/runtime_permission_handler.git"
//                    )
//
//                    developerConnection.set(
//                        "scm:git:ssh://github.com/oooplatform/runtime_permission_handler.git"
//                    )
//
//                    url.set(
//                        "https://github.com/oooplatform/runtime_permission_handler"
//                    )
//                }
//            }
//        }
//    }
//
//    repositories {
//        val repoDir: String = gradle.startParameter.projectProperties["maven.repo.dir"] ?: "$rootDir/libs/maven-repo"
//        maven {
//            url = uri(repoDir)
//        }
////
////        maven {
////            url = uri("https://nexus.x840.ru/repository/maven-releases/")
////            credentials {
////                username = contributorUserName
////                password = contributorPassword
////            }
////        }
//    }
//}
//
//signing{
//    sign(publishing.publications)
//}