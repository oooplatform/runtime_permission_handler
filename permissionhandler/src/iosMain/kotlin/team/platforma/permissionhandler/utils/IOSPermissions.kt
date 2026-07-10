package team.platforma.permissionhandler.utils

import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.permission.access.BluetoothAccess
import team.platforma.permissionhandler.permission.access.CalendarAccess
import team.platforma.permissionhandler.permission.access.CameraAccess
import team.platforma.permissionhandler.permission.access.ContactsAccess
import team.platforma.permissionhandler.permission.access.GalleryAccess
import team.platforma.permissionhandler.permission.access.GeolocationOnAlwaysAccess
import team.platforma.permissionhandler.permission.access.GeolocationOnAppUsingAccess
import team.platforma.permissionhandler.permission.access.MicrophoneAccess
import team.platforma.permissionhandler.permission.access.EmptyAccess
import team.platforma.permissionhandler.permission.access.PostNotificationAccess

internal object IOSPermissions {

    fun extract(
        permission: Permission
    ): PermissionRequester {
        return when (permission) {
            Permission.Gallery -> GalleryAccess(permission)
            Permission.Camera -> CameraAccess(permission)
            Permission.Microphone -> MicrophoneAccess(permission)
            Permission.PostNotifications -> PostNotificationAccess(permission)
            Permission.GeolocationApp -> GeolocationOnAppUsingAccess(permission)
            Permission.GeolocationBackground -> GeolocationOnAlwaysAccess(permission)
            Permission.Contacts -> ContactsAccess(permission)
            is Permission.ExternalStorage -> EmptyAccess(permission)
//            is Permission.WriteExternalStorage -> PlaceholderAccess(permission)
            Permission.Bluetooth -> BluetoothAccess(permission)
            is Permission.Calendar -> CalendarAccess(permission)
        }
    }
}