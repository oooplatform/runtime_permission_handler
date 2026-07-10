package team.platforma.permissionhandler.permission.access

import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.AccessPlaceholder
import team.platforma.permissionhandler.permission.PermissionRequester

internal class EmptyAccess(perm: Permission) : PermissionRequester(perm)  {
    override fun request(result: (Permission, Boolean) -> Unit) {
        result(
            permission,
            try {
                (permission as AccessPlaceholder).status
            } catch (e: Exception) {
                false
            }
        )
    }
}