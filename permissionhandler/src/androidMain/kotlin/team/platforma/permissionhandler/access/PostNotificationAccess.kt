package team.platforma.permissionhandler.access

import android.Manifest
import android.os.Build

internal val PostNotificationAccess
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        emptyArray()
    }