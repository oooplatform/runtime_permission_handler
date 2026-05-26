package team.platforma.permissionhandler.permission.access

import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusLimited
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHAuthorizationStatusRestricted
import platform.Photos.PHPhotoLibrary
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.isNotExistInPlist

internal class GalleryAccess(perm: Permission): PermissionRequester(perm) {
    private val status = PHPhotoLibrary.authorizationStatus()
    override fun request(result: (Permission, Boolean) -> Unit) {
        if ("NSPhotoLibraryUsageDescription".isNotExistInPlist()) return

        when (status) {

            PHAuthorizationStatusAuthorized,
            PHAuthorizationStatusLimited -> {
                result(permission, true)
            }

            PHAuthorizationStatusDenied,
            PHAuthorizationStatusRestricted -> {
                result(permission, false)
            }

            PHAuthorizationStatusNotDetermined -> {

                dispatch_async(dispatch_get_main_queue()) {

                    PHPhotoLibrary.requestAuthorization { newStatus ->

                        val granted =
                            newStatus == PHAuthorizationStatusAuthorized ||
                                    newStatus == PHAuthorizationStatusLimited

                        result(permission, granted)
                    }
                }
            }
        }
    }
}