<div align="center">

<h1>Runtime Permission Handler</h1>

<p>
Simple and lightweight runtime permission handling library for Kotlin Multiplatform Mobile (KMM).
</p>

<p>
<img src="https://img.shields.io/badge/Kotlin-Multiplatform-purple?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Platform-Android-green?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Platform-iOS-blue?style=for-the-badge"/>
<img src="https://img.shields.io/badge/License-MIT-orange?style=for-the-badge"/>
</p>

</div>

---

# ✨ Features

- ✅ Kotlin Multiplatform support
- ✅ Android & iOS
- ✅ Compose Multiplatform ready
- ✅ Simple callback API
- ✅ Multiple permission requests
- ✅ Permanent denial detection
- ✅ Location permissions support
- ✅ Lightweight and easy to integrate

---

### 📦 Installation

## Gradle

```kotlin
dependencies {
    
}
```
<h4>Supported Permissions</h4>
<table> 
  <thead> <tr> <th>Permission</th> <th>Android</th> <th>iOS</th> </tr></thead> 
  <tbody> 
    <tr> <td>Camera</td> <td>✅</td> <td>✅</td> </tr> 
    <tr> <td>Gallery</td> <td>✅</td> <td>✅</td> 
    </tr> <tr> <td>Microphone</td> <td>✅</td> <td>✅</td> 
    </tr> <tr> <td>PostNotifications</td> <td>✅</td> <td>✅</td> 
    </tr> <tr> <td>GeolocationOnAppUsing</td> <td>✅</td> <td>✅</td> 
    </tr> <tr> <td>GeolocationAlways</td> <td>✅</td> <td>✅</td> 
    </tr> <tr> <td>ReadContacts</td> <td>✅</td> <td>✅</td> 
    </tr> <tr> <td>WriteContacts</td> <td>✅</td> <td>✅</td> </tr> 
    </tr> <tr> <td>ReadExternalStorage</td> <td>✅</td> <td>✅</td> </tr> 
  </tr> <tr> <td>WriteExternalStorage</td> <td>✅</td> <td>✅</td> </tr> 
  </tbody> 
</table>

## 🚀 How to use
### On Andriod 
Initialize PermissionHandler object on MainActivity or Application
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        PermissionHandler.start(this)
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}
```
Register your permission in manifest.xml
```xml
<uses-permission android:name="android.permission.CAMERA" />
```

### On iOS
Register your permission in Info.plist
```xml
<key>NSCameraUsageDescription</key>
<string>We want to access the camera to take photos.</string>
```

## Compose Multiplatform Example
#### Check or request single permission
```kotlin
PermissionHandler.requestSingle(
    Permission.Camera
) { granted ->

    if (granted) {
        // Permission granted your code for start camera, and take photo
    } else {
        // Permission denied
    }
}
```

#### Check or request multiple permissions.
```kotlin
PermissionHandler.requestMultiple(
    Permission.Camera,
    Permission.Microphone
) { grantedMap ->

    val cameraGranted = grantedMap[Permission.Camera]
    val microphoneGranted = grantedMap[Permission.Microphone]
}
```

#### Check or request multiple permissions in a specific order.
```kotlin
PermissionHandler.requestMultiplySequential(
    Permission.GeolocationOnAppUsing,
    Permission.GeolocationAlways
) { grantedMap -> // Requests background location sequentially after obtaining "While Using the App" permission

  val backgroundGeolocation = grantedMap.all { (permission, granted) -> granted }

  if (backgroundGeolocation) {
    // Granted background geolocation start service
  } else {
    // Permission denied
  }
}
```


