package team.platforma.permissionhandler.permission.access

import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.isNotExistInPlist

internal class GeolocationOnAppUsingAccess(perm: Permission) : PermissionRequester(perm) {
    private val locationManager = CLLocationManager()
    private var delegate: LocationDelegate? = null

    override fun request(result: (Permission, Boolean) -> Unit) {
        if ("NSLocationWhenInUseUsageDescription".isNotExistInPlist()) return

        val status = CLLocationManager.Companion.authorizationStatus()

        when (status) {
            kCLAuthorizationStatusAuthorizedWhenInUse,
            kCLAuthorizationStatusAuthorizedAlways -> {
                result(permission, true)
            }

            kCLAuthorizationStatusDenied,
            kCLAuthorizationStatusRestricted -> {
                result(permission, false)
            }

            kCLAuthorizationStatusNotDetermined -> {
                delegate = LocationDelegate()
                locationManager.delegate = delegate
                delegate?.onResult { granted ->
                    delegate = null
                    result(permission, granted)
                }
                dispatch_async(dispatch_get_main_queue()) {
                    locationManager.requestWhenInUseAuthorization()
                }
            }
        }
    }

    private class LocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {
        private var result: ((Boolean) -> Unit)? = null
        fun onResult(boolean: (Boolean) -> Unit) {
            result = boolean
        }

        override fun locationManager(
            manager: CLLocationManager,
            didChangeAuthorizationStatus: CLAuthorizationStatus
        ) {
            when (didChangeAuthorizationStatus) {
                kCLAuthorizationStatusAuthorizedWhenInUse -> {
                    result?.invoke(true)
                }

                kCLAuthorizationStatusDenied,
                kCLAuthorizationStatusRestricted -> {
                    result?.invoke(false)
                }
            }
        }
    }
}