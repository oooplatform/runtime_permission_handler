package team.platforma.permissionhandler.utils

import platform.Foundation.NSBundle

internal fun String.isNotExistInPlist(): Boolean {
   return NSBundle.mainBundle.infoDictionary?.get(this) == null
}