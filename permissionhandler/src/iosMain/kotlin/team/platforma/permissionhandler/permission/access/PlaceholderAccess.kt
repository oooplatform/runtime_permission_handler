package team.platforma.permissionhandler.permission.access

import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester

internal class PlaceholderAccess(perm: Permission) : PermissionRequester(perm)  {
    override fun request(result: (Permission, Boolean) -> Unit) {
        result(permission, true)
    }
}