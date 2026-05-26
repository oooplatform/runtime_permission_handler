package team.platforma.permissionhandler.access

import android.Manifest

internal val ReadContactsAccess
    get() = arrayOf(Manifest.permission.READ_CONTACTS)

internal val WriteContactsAccess
    get() = arrayOf(Manifest.permission.WRITE_CONTACTS)