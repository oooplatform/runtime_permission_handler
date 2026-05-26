package team.platforma.permissionhandler.permission.access

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationState
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.isNotExistInPlist

internal class GeolocationOnAlwaysAccess(perm: Permission) : PermissionRequester(perm) {
    private val locationManager = CLLocationManager()

    override fun request(result: (Permission, Boolean) -> Unit) {
        if ("NSLocationAlwaysAndWhenInUseUsageDescription".isNotExistInPlist()) return

        when (CLLocationManager.authorizationStatus()) {
            kCLAuthorizationStatusAuthorizedWhenInUse -> {
                val requested = storage.boolForKey(ALWAYS_GEOLOCATION_REQUESTED)
                if (requested) {

                    result(permission, false)

                } else {

                    storage.setBool(true, ALWAYS_GEOLOCATION_REQUESTED)

                    dispatch_async(dispatch_get_main_queue()) {
                        locationManager.requestAlwaysAuthorization()
                        scope.launch {
                            delay(500)
                            do {
                                if (userChoose()) {
                                    when (CLLocationManager.authorizationStatus()) {
                                        kCLAuthorizationStatusAuthorizedWhenInUse -> {
                                            result(permission, false)
                                            break
                                        }

                                        kCLAuthorizationStatusAuthorizedAlways -> {
                                            result(permission, true)
                                            break
                                        }
                                    }
                                }
                            } while (true)
                        }
                    }
                }
            }

            kCLAuthorizationStatusAuthorizedAlways -> {
                result(permission, true)
            }

            kCLAuthorizationStatusDenied,
            kCLAuthorizationStatusRestricted-> {
                result(permission, false)
            }
        }
    }

    companion object {
        private const val ALWAYS_GEOLOCATION_REQUESTED = "always_geolocation_requested"
        private val storage = NSUserDefaults.standardUserDefaults()
        private val scope = CoroutineScope(Dispatchers.IO.limitedParallelism(1))
        private suspend fun userChoose(): Boolean = withContext(Dispatchers.Main) {
            return@withContext UIApplication.sharedApplication.applicationState == UIApplicationState.UIApplicationStateActive
        }
    }
}