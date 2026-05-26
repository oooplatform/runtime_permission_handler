package team.platforma.permissionhandler.permission.access

import platform.Contacts.CNAuthorizationStatusAuthorized
import platform.Contacts.CNAuthorizationStatusDenied
import platform.Contacts.CNAuthorizationStatusLimited
import platform.Contacts.CNAuthorizationStatusNotDetermined
import platform.Contacts.CNAuthorizationStatusRestricted
import platform.Contacts.CNContactStore
import platform.Contacts.CNEntityType
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.isNotExistInPlist

internal class ContactsAccess(perm: Permission): PermissionRequester(perm) {
    private val entityType = CNEntityType.CNEntityTypeContacts
    private val status = CNContactStore.authorizationStatusForEntityType(entityType)

    override fun request(result: (Permission, Boolean) -> Unit) {
        if ("NSContactsUsageDescription".isNotExistInPlist()) return

        when (status) {
            CNAuthorizationStatusAuthorized,
            CNAuthorizationStatusLimited -> {
                result(permission, true)
            }

            CNAuthorizationStatusDenied,
            CNAuthorizationStatusRestricted -> {
                result(permission, false)
            }

            CNAuthorizationStatusNotDetermined -> {
                dispatch_async(dispatch_get_main_queue()) {
                    CNContactStore().requestAccessForEntityType(
                        entityType
                    ) { granted, _ ->
                        result(permission, granted)
                    }
                }
            }
        }
    }
}