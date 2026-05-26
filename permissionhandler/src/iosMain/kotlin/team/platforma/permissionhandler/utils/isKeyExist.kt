package team.platforma.permissionhandler.utils

import platform.Foundation.NSBundle

fun String.isNotExistInPlist(): Boolean {
   return NSBundle.mainBundle.infoDictionary?.get(this) == null
}