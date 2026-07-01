package team.platforma.permissionhandler

/**
 * Object for request runtime permissions
 *
 * See also:
 *
 * [requestSingle]
 *
 * [requestMultiply]
 *
 * [requestMultiplySequential]
 *
 * For Android need to initialize on MainActivity or Application
 * ```
 * class MainActivity : ComponentActivity() {
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         PermissionHandler.start(this) //<- initialize like this!
 *         ...
 *         super.onCreate(savedInstanceState)
 *
 *         setContent {
 *             App()
 *         }
 *     }
 * }
 * ```
 */
expect object PermissionHandler {
    /**
     * Use function for request single permission
     *
     * See also [requestMultiply]
     *
     * For user click
     * ```kotlin
     *             Button(
     *                 onClick = {
     *                     PermissionHandler.requestSingle(
     *                         Permission.Camera
     *                     ) { granted ->
     *                         //... result for Camera access granted or not granted
     *                     }
     *                 }
     *             ) {
     *                 Text("Request camera access")
     *             }
     * ```
     * Automatically after screen started
     * ```kotlin
     *        LaunchedEffect(Unit) {
     *             PermissionHandler.requestSingle(
     *                 Permission.GeolocationOnAppUsing
     *             ) { granted ->
     *                 //... result for Geolocation on app using access granted or not granted
     *             }
     *         }
     * ```
     */
    fun requestSingle(
        permission: Permission,
        result: (Boolean) -> Unit
    )

    /**
     * Use this function to request multiple permissions. On Android, the order of permission requests will be determined by the system. On iOS, permissions will be requested in the order they are passed to the function.
     *
     * See also [requestMultiplySequential]
     * ```kotlin
     *                PermissionHandler.requestMultiply(
     *                     Permission.Camera,
     *                     Permission.Gallery,
     *                     Permission.Microphone,
     *                     Permission.PostNotifications,
     *                     Permission.ReadContacts
     *                 ) { result ->
     *                     // ...resultMap for multiple queries
     *                 }
     * ```
     */
    fun requestMultiply(
        vararg permissions: Permission,
        result: (Map<Permission, Boolean>) -> Unit
    )
    /**
     * Use this function to request multiple permissions. On Android, the order of permission requests will be determined by the system. On iOS, permissions will be requested in the order they are passed to the function.
     *
     * See also [requestMultiplySequential]
     * ```kotlin
     *                PermissionHandler.requestMultiply(
     *                     Permission.Camera,
     *                     Permission.Gallery,
     *                     Permission.Microphone,
     *                     Permission.PostNotifications,
     *                     Permission.ReadContacts
     *                 ) { result ->
     *                     // ...resultMap for multiple queries
     *                 }
     * ```
     */
    fun requestMultiply(
        list: List<Permission>,
        result: (Map<Permission, Boolean>) -> Unit
    )

    /**
     * Some permissions can only be requested in a strictly defined order.
     * For example, geolocation access while the app is running, followed by an update to enable geolocation in the background.
     * Use this function to request multiple permissions in the order passed to the function.
     * ``` kotlin
     *                 PermissionHandler.requestMultiplySequential(
     *                     Permission.GeolocationApp,
     *                     Permission.GeolocationBackground
     *                 ) { granted ->
     *                     // ...resultMap for multiple queries
     *                 }
     * ```
     */
    fun requestMultiplySequential(
        vararg permissions: Permission,
        result: (Map<Permission, Boolean>) -> Unit
    )
    /**
     * Some permissions can only be requested in a strictly defined order.
     * For example, geolocation access while the app is running, followed by an update to enable geolocation in the background.
     * Use this function to request multiple permissions in the order passed to the function.
     * ``` kotlin
     *                 PermissionHandler.requestMultiplySequential(
     *                     Permission.GeolocationApp,
     *                     Permission.GeolocationBackground
     *                 ) { granted ->
     *                     // ...resultMap for multiple queries
     *                 }
     * ```
     */
    fun requestMultiplySequential(
        list: List<Permission>,
        result: (Map<Permission, Boolean>) -> Unit
    )
}

