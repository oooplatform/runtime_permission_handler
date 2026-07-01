package team.platforma.permissionhandler.permission.access

import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerAuthorizationDenied
import platform.CoreBluetooth.CBManagerAuthorizationNotDetermined
import platform.CoreBluetooth.CBManagerAuthorizationRestricted
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.isNotExistInPlist

internal class BluetoothAccess(perm: Permission) : PermissionRequester(perm) {
    override fun request(result: (Permission, Boolean) -> Unit) {
        if ("NSBluetoothAlwaysUsageDescription".isNotExistInPlist()) return

        when (CBCentralManager.authorization) {
            CBManagerAuthorizationAllowedAlways -> {
                result(permission, true)
            }

            CBManagerAuthorizationDenied,
            CBManagerAuthorizationRestricted -> {
                result(permission, false)
            }

            CBManagerAuthorizationNotDetermined -> {
                val delegate = BluetoothDelegate()

                delegate.onResult { bool ->
                    result(permission, bool)
                }

                dispatch_async(dispatch_get_main_queue()) {
                    CBCentralManager(delegate = delegate, queue = null)
                }
            }
        }
    }

    private class BluetoothDelegate() : NSObject(), CBCentralManagerDelegateProtocol {
        private var result: ((Boolean) -> Unit)? = null
        fun onResult(boolean: (Boolean) -> Unit) {
            result = boolean
        }

        override fun centralManagerDidUpdateState(central: CBCentralManager) {
            when (central.authorization) {
                CBManagerAuthorizationAllowedAlways -> {
                    result?.invoke(true)
                }

                CBManagerAuthorizationDenied,
                CBManagerAuthorizationRestricted -> {
                    result?.invoke(false)
                }
            }
        }
    }
}