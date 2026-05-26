package team.platforma.permissionhandler.utils

import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.permission.access.CameraAccess
import team.platforma.permissionhandler.permission.access.ContactsAccess
import team.platforma.permissionhandler.permission.access.GalleryAccess
import team.platforma.permissionhandler.permission.access.GeolocationOnAlwaysAccess
import team.platforma.permissionhandler.permission.access.GeolocationOnAppUsingAccess
import team.platforma.permissionhandler.permission.access.MicrophoneAccess
import team.platforma.permissionhandler.permission.access.PlaceholderAccess
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
            Permission.GeolocationOnAppUsing -> GeolocationOnAppUsingAccess(permission)
            Permission.GeolocationAlways -> GeolocationOnAlwaysAccess(permission)
            Permission.ReadContacts -> ContactsAccess(permission)
            Permission.WriteContacts -> ContactsAccess(permission)
            Permission.ReadExternalStorage -> PlaceholderAccess(permission)
            Permission.WriteExternalStorage -> PlaceholderAccess(permission)
        }
    }
}