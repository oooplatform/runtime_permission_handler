package team.platforma.permissionhandler.access

import android.Manifest

internal val ContactsAccess
    get() = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )