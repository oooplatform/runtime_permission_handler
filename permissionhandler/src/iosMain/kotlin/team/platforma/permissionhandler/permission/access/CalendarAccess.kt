package team.platforma.permissionhandler.permission.access

import platform.EventKit.EKAuthorizationStatusDenied
import platform.EventKit.EKAuthorizationStatusFullAccess
import platform.EventKit.EKAuthorizationStatusNotDetermined
import platform.EventKit.EKAuthorizationStatusRestricted
import platform.EventKit.EKAuthorizationStatusWriteOnly
import platform.EventKit.EKEntityType
import platform.EventKit.EKEventStore
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.isNotExistInPlist

internal class CalendarAccess(private val perm: Permission.Calendar) : PermissionRequester(perm) {
    private val entityType = EKEntityType.EKEntityTypeEvent

    override fun request(result: (Permission, Boolean) -> Unit) {
        if ("NSCalendarsUsageDescription".isNotExistInPlist()) return

        when (EKEventStore.authorizationStatusForEntityType(entityType)) {

            EKAuthorizationStatusNotDetermined -> requestPermission(result)

            EKAuthorizationStatusFullAccess ->
                result(permission, true)

            EKAuthorizationStatusWriteOnly ->
                result(
                    permission,
                    perm.access == Permission.Calendar.Access.WriteOnly
                )

            EKAuthorizationStatusDenied,
            EKAuthorizationStatusRestricted ->
                result(permission, false)

            else ->
                result(permission, false)
        }
    }

    private fun requestPermission(result: (Permission, Boolean) -> Unit) {

        dispatch_async(dispatch_get_main_queue()) {
            when (perm.access) {
                Permission.Calendar.Access.Full ->
                    EKEventStore().requestFullAccessToEventsWithCompletion { granted, _ ->
                        result(permission, granted)
                    }

                Permission.Calendar.Access.WriteOnly ->
                    EKEventStore().requestWriteOnlyAccessToEventsWithCompletion { granted, _ ->
                        result(permission, granted)
                    }
            }
        }
    }
}