package team.platforma.permissionhandler.access

import android.Manifest
import team.platforma.permissionhandler.Permission

internal class Calendar(private val permission: Permission.Calendar) {

    internal fun request(): Array<String> {
        return when (permission.access) {
            Permission.Calendar.Access.Full -> {
                arrayOf(
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR
                )
            }
            Permission.Calendar.Access.WriteOnly -> {
                arrayOf(
                    Manifest.permission.WRITE_CALENDAR
                )
            }
        }
    }
}


