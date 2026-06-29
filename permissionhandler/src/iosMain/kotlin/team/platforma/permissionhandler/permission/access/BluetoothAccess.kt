package team.platforma.permissionhandler.permission.access

import platform.CoreBluetooth.CBCentralManager
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester

internal class BluetoothAccess(perm: Permission) : PermissionRequester(perm) {
    override fun request(result: (Permission, Boolean) -> Unit) {
        var manager: CBCentralManager? = null
        manager = CBCentralManager(
            delegate = null,
            queue = null
        )
    }
}