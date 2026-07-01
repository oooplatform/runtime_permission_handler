package team.platforma.permissionhandlerlibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.PermissionHandler

@Composable
@Preview
fun App() {
    MaterialTheme {

        LaunchedEffect(Unit) {
//            PermissionHandler.requestSingle(
//                Permission.GeolocationOnAppUsing
//            ) { granted ->
//                //... result for Geolocation on app using access granted or not granted
//            }
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    PermissionHandler.requestSingle(
                        Permission.Camera
                    ) { granted ->
                        //... result for Camera access granted or not granted
                    }
                }
            ) {
                Text("Camera")
            }
            Button(onClick = {
                PermissionHandler.requestSingle(
                    Permission.PostNotifications
                ) { granted ->
                    println("PERM_LOG Permission.PostNotifications $granted")
                }
            }) {
                Text("PostNotifications")
            }

            Button(onClick = {
                PermissionHandler.requestSingle(
                    Permission.Gallery
                ) { granted ->
                    println("PERM_LOG Permission.Gallery $granted")
                }
            }) {
                Text("Gallery")
            }

            Button(onClick = {
                PermissionHandler.requestSingle(
                    Permission.GeolocationApp
                ) { granted ->
                    println("PERM_LOG Permission.GeolocationOnAppUsing $granted")
                }
            }) {
                Text("GeolocationOnAppUsing")
            }
            Button(onClick = {
                PermissionHandler.requestSingle(
                    Permission.GeolocationBackground
                ) { always ->
                    println("PERM_LOG Permission.GeolocationAlways $always")
                }
            }) {
                Text("GeolocationAlways")
            }

            Button(onClick = {
                PermissionHandler.requestMultiplySequential(
                    Permission.GeolocationApp,
                    Permission.GeolocationBackground
                ) { grantedMap ->
                    val backgroundGeolocation = grantedMap.all { (permission, granted) -> granted }
                    if (backgroundGeolocation) {
                        // Granted background geolocation start service
                    } else {
                        // Permission denied
                    }
                }
            }) {
                Text("Geolocation always Sequential")
            }

            Button(onClick = {
                PermissionHandler.requestSingle(
                    Permission.Contacts
                ) { granted ->
                    println("PERM_LOG Permission.Contacts $granted")
                }
            }) {
                Text("ReadContacts")
            }

//            Button(onClick = {
//                PermissionHandler.requestSingle(
//                    Permission.WriteContacts
//                ) { granted ->
//                    println("PERM_LOG Permission.WriteContacts $granted")
//                }
//            }) {
//                Text("WriteContacts")
//            }

            Button(onClick = {
                PermissionHandler.requestSingle(
                    Permission.Microphone
                ) { granted ->
                    println("PERM_LOG Permission.Microphone $granted")
                }
            }) {
                Text("Microphone")
            }

            Button(onClick = {
                PermissionHandler.requestMultiply(
                    Permission.Bluetooth,
                    Permission.Camera,
                    Permission.Gallery,
                    Permission.Microphone,
                    Permission.PostNotifications,
                    Permission.GeolocationApp,
                    Permission.Contacts
                ) { result ->
                    println("PERM_LOG Multiply :")
                    result.forEach { (key, value) ->
                        println("$key $value")
                    }

                }
            }) {
                Text("Multiple request")
            }

            Button(onClick = {
                PermissionHandler.requestSingle(
                    Permission.Bluetooth
                ) { granted ->
                    println("PERM_LOG Permission.Bluetooth $granted")
                }
            }) {
                Text("Bluetooth")
            }
        }
    }
}