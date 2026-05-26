package team.platforma.permissionhandler.permission.access

import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.isNotExistInPlist

internal class CameraAccess(perm: Permission): PermissionRequester(perm) {
    private val mediaType = AVMediaTypeVideo
    private val status = AVCaptureDevice.authorizationStatusForMediaType(mediaType)

    override fun request(result: (Permission, Boolean) -> Unit) {
        if ("NSCameraUsageDescription".isNotExistInPlist()) return

        when (status) {
            AVAuthorizationStatusAuthorized -> {
                result(permission, true)
            }

            AVAuthorizationStatusNotDetermined -> {
                dispatch_async(dispatch_get_main_queue()) {
                    AVCaptureDevice.requestAccessForMediaType(mediaType) { granted ->
                        result(permission, granted)
                    }
                }
            }

            else -> {
                result(permission, false)
            }
        }
    }
}