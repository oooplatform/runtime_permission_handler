package team.platforma.permissionhandler.permission.access

import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionRecordPermissionDenied
import platform.AVFAudio.AVAudioSessionRecordPermissionGranted
import platform.AVFAudio.AVAudioSessionRecordPermissionUndetermined
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import team.platforma.permissionhandler.Permission
import team.platforma.permissionhandler.permission.PermissionRequester
import team.platforma.permissionhandler.utils.isNotExistInPlist

internal class MicrophoneAccess(perm: Permission): PermissionRequester(perm) {
    private val session = AVAudioSession.sharedInstance()
    private val status = session.recordPermission()

    override fun request(result: (Permission, Boolean) -> Unit) {
        if ("NSMicrophoneUsageDescription".isNotExistInPlist()) return

        when (status) {

            AVAudioSessionRecordPermissionGranted -> {
                result(permission, true)
            }

            AVAudioSessionRecordPermissionDenied -> {
                result(permission, false)
            }

            AVAudioSessionRecordPermissionUndetermined -> {
                dispatch_async(dispatch_get_main_queue()) {
                    session.requestRecordPermission { granted ->
                        result(permission, granted)
                    }
                }
            }
        }
    }
}