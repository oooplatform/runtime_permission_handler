package team.platforma.permissionhandler.access

import android.Manifest
import team.platforma.permissionhandler.Permission


internal val ReadExternalStorageAccess
    get() = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

internal val WriteExternalStorageAccess
    get() = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)