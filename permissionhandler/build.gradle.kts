import org.apache.log4j.BasicConfigurator.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.mavenPublish)
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

val secretsFile = file(System.getProperty("user.home") + "/.maven-central/credentials.properties")

if (secretsFile.exists()) {
    val props = Properties().apply {
        secretsFile.inputStream().use { load(it) }
    }

    props.forEach { (k, v) ->
        System.setProperty(k.toString(), v.toString())
    }
}

group = "io.github.evgeny5454"
version = "0.0.1"

mavenPublishing {
    configure(

    )
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = group.toString(),
        artifactId = "permission-handler",
        version = version.toString()
    )

    pom {
        name.set("Permission Handler")
        description.set("PermissionHandler")
        inceptionYear.set("2026")
        url.set("https://github.com/oooplatform/runtime_permission_handler")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/license/mit")
            }
        }

        developers {
            developer {
                id.set("oooplatform")
                name.set("smirnovdv98")
            }
        }

        scm {
            url.set("https://github.com/oooplatform/runtime_permission_handler")
            connection.set("scm:git:git://github.com/oooplatform/runtime_permission_handler.git")
            developerConnection.set("scm:git:ssh://github.com/oooplatform/runtime_permission_handler.git")
        }
    }
}