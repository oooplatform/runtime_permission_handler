package team.platforma.permissionhandler

import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.IOSPermissions

actual object PermissionHandler {
    actual fun requestSingle(
        permission: Permission,
        result: (Boolean) -> Unit
    ) {
        IOSPermissions.extract(permission)
            .request { _, granted ->
                result(granted)
            }
    }

    actual fun requestMultiply(
        vararg permissions: Permission,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        MultiplyRequestEngine(
            permissions.map { IOSPermissions.extract(it) }
                .distinct()
        ).request { resultMap ->
            result(resultMap)
        }
    }

    actual fun requestMultiply(
        list: List<Permission>,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        MultiplyRequestEngine(
            list.map { IOSPermissions.extract(it) }
                .distinct()
        ).request { resultMap ->
            result(resultMap)
        }

    }

    actual fun requestMultiplySequential(
        vararg permissions: Permission,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        MultiplyRequestEngine(
            permissions.map { IOSPermissions.extract(it) }
                .distinct()
        ).request { resultMap ->
            result(resultMap)
        }
    }

    actual fun requestMultiplySequential(
        list: List<Permission>,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        MultiplyRequestEngine(
            list.map { IOSPermissions.extract(it) }
                .distinct()
        ).request { resultMap ->
            result(resultMap)
        }
    }

    private class MultiplyRequestEngine(private val list: List<PermissionRequester>) {
        private var resultMap: MutableMap<Permission, Boolean> = mutableMapOf()
        fun request(
            result: (Map<Permission, Boolean>) -> Unit
        ) {
            fun requestNextPermission(index: Int) {
                if (index < list.size) {
                    list[index].request { permission, granted ->
                        resultMap[permission] = granted
                        requestNextPermission(index + 1)
                    }
                } else {
                    result(resultMap)
                }
            }
            requestNextPermission(0)
        }
    }
}