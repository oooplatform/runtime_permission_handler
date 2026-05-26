package team.platforma.permissionhandlerlibrary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform