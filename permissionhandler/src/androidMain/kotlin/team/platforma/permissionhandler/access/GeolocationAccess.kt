package team.platforma.permissionhandler.access

import android.Manifest
import android.os.Build

internal val GeolocationOnAppUsingAccess
    get() = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

internal val GeolocationAlwaysAccess
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    } else {
        emptyArray()
    }

