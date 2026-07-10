package team.platforma.permissionhandler.access

import android.Manifest
import team.platforma.permissionhandler.Permission


internal val ReadExternalStorageAccess
    get() = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

internal val WriteExternalStorageAccess
    get() = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

internal class ExternalStorage(private val permission: Permission.ExternalStorage){

    internal fun request(): Array<String> {
        return when (permission.access) {
            Permission.ExternalStorage.Access.Full -> {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
            Permission.ExternalStorage.Access.Write -> {
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }

            Permission.ExternalStorage.Access.Read -> {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }
}