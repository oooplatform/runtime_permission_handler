package team.platforma.permissionhandler.utils

import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.access.BluetoothAccess
import team.platforma.permissionhandler.access.CameraAccess
import team.platforma.permissionhandler.access.GalleryAccess
import team.platforma.permissionhandler.access.GeolocationAlwaysAccess
import team.platforma.permissionhandler.access.GeolocationOnAppUsingAccess
import team.platforma.permissionhandler.access.MicrophoneAccess
import team.platforma.permissionhandler.access.PostNotificationAccess
import team.platforma.permissionhandler.access.ReadContactsAccess
import team.platforma.permissionhandler.access.ReadExternalStorageAccess
import team.platforma.permissionhandler.access.WriteContactsAccess
import team.platforma.permissionhandler.access.WriteExternalStorageAccess

internal object AndroidPermissions {
    fun extract(permission: Permission): Array<String> {
        return when (permission) {
            Permission.Camera -> CameraAccess
            Permission.Gallery -> GalleryAccess
            Permission.Microphone -> MicrophoneAccess
            Permission.PostNotifications -> PostNotificationAccess
            Permission.GeolocationOnAppUsing -> GeolocationOnAppUsingAccess
            Permission.GeolocationAlways -> GeolocationAlwaysAccess
            Permission.ReadContacts -> ReadContactsAccess
            Permission.WriteContacts -> WriteContactsAccess
            Permission.ReadExternalStorage -> ReadExternalStorageAccess
            Permission.WriteExternalStorage -> WriteExternalStorageAccess
            Permission.Bluetooth -> BluetoothAccess
        }
    }
}