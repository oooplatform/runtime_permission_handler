package team.platforma.permissionhandler

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import team.platforma.permissionhandler.utils.AndroidPermissions

actual object PermissionHandler {
    fun start(activity: ComponentActivity) =
        initializePermissionLauncher(activity)

    fun start(application: Application) =
        initializePermissionLauncher(application.applicationContext as ComponentActivity)

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var singlePermissionResult: ((Boolean) -> Unit)? = null
    private fun onSinglePermissionResult(boolean: (Boolean) -> Unit) {
        singlePermissionResult = boolean
    }

    private var multiplyPermissionResult: ((Map<String, Boolean>) -> Unit)? = null
    private fun onMultiplyPermissionResult(result: (Map<String, Boolean>) -> Unit) {
        multiplyPermissionResult = result
    }

    private fun initializePermissionLauncher(componentActivity: ComponentActivity) {
        if (!::permissionLauncher.isInitialized) {
            permissionLauncher = componentActivity.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { multiplyPermissions ->
                singlePermissionResult?.invoke(multiplyPermissions.values.all { it })
                multiplyPermissionResult?.invoke(multiplyPermissions)
            }
        }
    }


    actual fun requestSingle(
        permission: Permission,
        result: (Boolean) -> Unit
    ) {
        if (!::permissionLauncher.isInitialized) initializeException()
        getSinglePermissionResult(AndroidPermissions.extract(permission)) { granted ->
            result(granted)
        }
    }

    private fun getSinglePermissionResult(permission: Array<String>, result: (Boolean) -> Unit) {
        permissionLauncher.launch(permission)
        onSinglePermissionResult { granted ->
            result(granted)
            singlePermissionResult = null
        }
    }

    actual fun requestMultiply(
        vararg permissions: Permission,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        if (!::permissionLauncher.isInitialized) initializeException()

        val permissionMap: Map<Permission, Array<String>> = permissions
            .associateWith { AndroidPermissions.extract(it) }

        getMultiplyPermissionResult(permissionMap) { result ->
            result(result)
        }
    }

    actual fun requestMultiply(
        list: List<Permission>,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        if (!::permissionLauncher.isInitialized) initializeException()

        val permissionMap = list.associateWith {
            AndroidPermissions.extract(it)
        }

        getMultiplyPermissionResult(permissionMap) { mapResult ->
            result(mapResult)
        }
    }

    private fun getMultiplyPermissionResult(
        permissionMap: Map<Permission, Array<String>>,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        val request: Array<String> = permissionMap
            .values
            .flatMap { it.toList() }
            .distinct()
            .toTypedArray()

        permissionLauncher.launch(request)

        onMultiplyPermissionResult { resultMap ->
            val finalResult = permissionMap
                .mapValues { (_, permissionsArray) ->
                    permissionsArray.all { runtimePermission ->
                        resultMap[runtimePermission] == true
                    }
                }
            result(finalResult)
            multiplyPermissionResult = null
        }
    }

    actual fun requestMultiplySequential(
        vararg permissions: Permission,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        sequentialRequest(
            list = permissions.toList(),
            result = result
        )
    }

    actual fun requestMultiplySequential(
        list: List<Permission>,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        sequentialRequest(
            list = list,
            result = result
        )
    }

    private fun sequentialRequest(
        list: List<Permission>,
        result: (Map<Permission, Boolean>) -> Unit
    ) {
        if (!::permissionLauncher.isInitialized) initializeException()

        CoroutineScope(Dispatchers.Main).launch {

            val map = mutableMapOf<Permission, Boolean>()

            for (permission in list) {
                val granted = requestSingleSequential(permission)
                map[permission] = granted
            }

            result(map)
        }
    }

    suspend fun requestSingleSequential(permission: Permission): Boolean =
        suspendCancellableCoroutine { cont ->

            requestSingle(permission) { granted ->
                if (cont.isActive) {
                    cont.resume(granted) { cause, _, _ -> }
                }
            }
        }




    private fun initializeException() {
        throw Exception(
            """
            
            
            PermissionHandler may be started in MainActivity or Application
            
            class MainActivity : ComponentActivity() {
                override fun onCreate(savedInstanceState: Bundle?) {
                    PermissionHandler.start(this)  <-- initialization example  
                    enableEdgeToEdge()
                    super.onCreate(savedInstanceState)

                    setContent {
                        App()
                    }
                }
            }
            
        """.trimIndent()
        )
    }

}