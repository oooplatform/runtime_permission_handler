package team.platforma.permissionhandler

/**
 * Available permissions:
 *
 * [Camera]
 *
 * [Gallery]
 *
 * [Microphone]
 *
 * [PostNotifications]
 *
 * [GeolocationApp]
 *
 * [GeolocationBackground]
 *
 * [Contacts]
 *
 * [ReadExternalStorage]
 *
 * [WriteExternalStorage]
 *
 * [Bluetooth]
 */
sealed class Permission {
    /**
     * Android Manifest
     * ```
     *     <uses-feature
     *         android:name="android.hardware.camera"
     *         android:required="false" />
     *     <uses-permission android:name="android.permission.CAMERA" />
     * ```
     * iOS Info.plist
     * ```
     * <key>NSCameraUsageDescription</key>
     * <string>Camera access</string>
     * ```
     */
    data object Camera : Permission()

    /**
     * Android Manifest
     * ```
     *     <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
     *     <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
     *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" android:maxSdkVersion="32" />
     * ```
     * iOS Info.plist
     * ```
     * <key>NSPhotoLibraryUsageDescription</key>
     * <string>Gallery access</string>
     * ```
     */
    data object Gallery : Permission()

    /**
     * Android Manifest
     * ```
     *    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
     * ```
     * iOS Info.plist
     * ```
     * <key>NSMicrophoneUsageDescription</key>
     * <string>Microphone access</string>
     * ```
     */
    data object Microphone : Permission()

    /**
     * Android Manifest
     * ```
     *    <uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
     * ```
     */
    data object PostNotifications : Permission()

    /**
     * Android Manifest
     * ```
     *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     * ```
     * iOS Info.plist
     * ```
     * <key>NSLocationWhenInUseUsageDescription</key>
     * <string>Geolocation on app using access</string>
     * ```
     */
    data object GeolocationApp : Permission()

    /**
     * Android Manifest
     * ```
     *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     *     <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION"/>
     * ```
     * iOS Info.plist
     * ```
     * <key>NSLocationWhenInUseUsageDescription</key>
     * <string>Geolocation on app using access</string>
     * <key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
     * <string>Geolocation background access</string>
     * ```
     */
    data object GeolocationBackground : Permission()

    /**
     * Android Manifest
     * ```
     *    <uses-permission android:name="android.permission.READ_CONTACTS"/>
     *    <uses-permission android:name="android.permission.WRITE_CONTACTS"/>
     * ```
     * iOS Info.plist
     * ```
     * <key>NSContactsUsageDescription</key>
     * <string>Phone contacts access</string>
     * ```
     */
    data object Contacts : Permission()

//    /**
//     * Android Manifest
//     * ```
//     *    <uses-permission android:name="android.permission.WRITE_CONTACTS"/>
//     * ```
//     * iOS Info.plist
//     * ```
//     * <key>NSContactsUsageDescription</key>
//     * <string>Phone contacts access</string>
//     * ```
//     */
//    data object WriteContacts : Permission()

    /**
     * Android Manifest
     * ```
     *    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" android:maxSdkVersion="32" />
     * ```
     */
    data object ReadExternalStorage : Permission()

    /**
     * Android Manifest
     * ```
     *    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="32" />
     * ```
     */
    data object WriteExternalStorage : Permission()

    /**
     * Android Manifest
     * ```
     *     <uses-permission android:name="android.permission.BLUETOOTH" android:maxSdkVersion="30" />
     *     <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" android:maxSdkVersion="30" />
     *     <uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE" android:maxSdkVersion="32" />
     *     <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" android:maxSdkVersion="32" />
     *     <uses-permission android:name="android.permission.BLUETOOTH_SCAN" android:maxSdkVersion="32" />
     * ```
     *
     * iOS Info.plist
     * ```
     * <key>NSBluetoothAlwaysUsageDescription</key>
     * <string>Приложению нужен Bluetooth для поиска и подключения к устройствам.</string>
     * ```
     */
    data object Bluetooth : Permission()

}