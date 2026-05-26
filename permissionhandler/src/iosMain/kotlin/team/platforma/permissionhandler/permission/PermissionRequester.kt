package team.platforma.permissionhandler.permission

import team.platforma.permissionhandler.Permission

internal abstract class PermissionRequester(perm: Permission) {
    protected val permission: Permission = perm
    abstract fun request(result: (Permission, Boolean) -> Unit)
}