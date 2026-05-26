package team.platforma.permissionhandler.permission.access

import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusEphemeral
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNAuthorizationStatusProvisional
import platform.UserNotifications.UNUserNotificationCenter
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester


internal class PostNotificationAccess(perm: Permission): PermissionRequester(perm) {
    private val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

    override fun request(result: (Permission, Boolean) -> Unit) {
        notificationCenter.getNotificationSettingsWithCompletionHandler { settings ->
            settings?.authorizationStatus?.let { status ->
                when (status) {
                    UNAuthorizationStatusAuthorized,
                    UNAuthorizationStatusProvisional,
                    UNAuthorizationStatusEphemeral -> {
                        result(permission, true)
                    }

                    UNAuthorizationStatusDenied -> {
                        result(permission, false)
                    }

                    UNAuthorizationStatusNotDetermined -> {
                        dispatch_async(dispatch_get_main_queue()) {
                            notificationCenter.requestAuthorizationWithOptions(
                                options = UNAuthorizationOptionAlert or
                                        UNAuthorizationOptionSound or
                                        UNAuthorizationOptionBadge,
                                completionHandler = { boolean, _ ->
                                    result(permission, boolean)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}