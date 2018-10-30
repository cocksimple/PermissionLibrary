package com.guagua.live.permissionlibrary.lib


import android.Manifest
import android.app.usage.UsageStatsManager
import android.text.TextUtils
import java.util.HashMap

object PermissionOps {
    /** @hide No operation specified.
     */
    private val OP_NONE = -1
    /** @hide Access to coarse location information.
     */
    private val OP_COARSE_LOCATION = 0

    /** @hide Access to fine location information.
     */
    private val OP_FINE_LOCATION = 1

    /** @hide Causing GPS to run.
     */
    private val OP_GPS = 2
    /** @hide
     */
    private val OP_VIBRATE = 3

    /** @hide
     */
    private val OP_READ_CONTACTS = 4

    /** @hide
     */
    private val OP_WRITE_CONTACTS = 5

    /** @hide
     */
    private val OP_READ_CALL_LOG = 6

    /** @hide
     */
    private val OP_WRITE_CALL_LOG = 7

    /** @hide
     */
    private val OP_READ_CALENDAR = 8

    /** @hide
     */
    private val OP_WRITE_CALENDAR = 9

    /** @hide
     */
    private val OP_WIFI_SCAN = 10
    /** @hide
     */
    private val OP_POST_NOTIFICATION = 11
    /** @hide
     */
    private val OP_NEIGHBORING_CELLS = 12
    /** @hide
     */
    private val OP_CALL_PHONE = 13

    /** @hide
     */
    private val OP_READ_SMS = 14

    /** @hide
     */
    private val OP_WRITE_SMS = 15
    /** @hide
     */
    private val OP_RECEIVE_SMS = 16

    /** @hide
     */
    private val OP_RECEIVE_EMERGECY_SMS = 17
    /** @hide
     */
    private val OP_RECEIVE_MMS = 18

    /** @hide
     */
    private val OP_RECEIVE_WAP_PUSH = 19

    /** @hide
     */
    private val OP_SEND_SMS = 20

    /** @hide
     */
    private val OP_READ_ICC_SMS = 21
    /** @hide
     */
    private val OP_WRITE_ICC_SMS = 22
    /** @hide
     */
    private val OP_WRITE_SETTINGS = 23

    /** @hide
     */
    private val OP_SYSTEM_ALERT_WINDOW = 24

    /** @hide
     */
    private val OP_ACCESS_NOTIFICATIONS = 25
    /** @hide
     */
    private val OP_CAMERA = 26

    /** @hide
     */
    private val OP_RECORD_AUDIO = 27

    /** @hide
     */
    private val OP_PLAY_AUDIO = 28
    /** @hide
     */
    private val OP_READ_CLIPBOARD = 29
    /** @hide
     */
    private val OP_WRITE_CLIPBOARD = 30
    /** @hide
     */
    private val OP_TAKE_MEDIA_BUTTONS = 31
    /** @hide
     */
    private val OP_TAKE_AUDIO_FOCUS = 32
    /** @hide
     */
    private val OP_AUDIO_MASTER_VOLUME = 33
    /** @hide
     */
    private val OP_AUDIO_VOICE_VOLUME = 34
    /** @hide
     */
    private val OP_AUDIO_RING_VOLUME = 35
    /** @hide
     */
    private val OP_AUDIO_MEDIA_VOLUME = 36
    /** @hide
     */
    private val OP_AUDIO_ALARM_VOLUME = 37
    /** @hide
     */
    private val OP_AUDIO_NOTIFICATION_VOLUME = 38
    /** @hide
     */
    private val OP_AUDIO_BLUETOOTH_VOLUME = 39
    /** @hide
     */
    private val OP_WAKE_LOCK = 40

    /** @hide Continually monitoring location data.
     */
    private val OP_MONITOR_LOCATION = 41
    /** @hide Continually monitoring location data with a relatively high power request.
     */
    private val OP_MONITOR_HIGH_POWER_LOCATION = 42
    /** @hide Retrieve current usage stats via [UsageStatsManager].
     */
    private val OP_GET_USAGE_STATS = 43
    /** @hide
     */
    private val OP_MUTE_MICROPHONE = 44
    /** @hide
     */
    private val OP_TOAST_WINDOW = 45
    /** @hide Capture the device's display contents and/or audio
     */
    private val OP_PROJECT_MEDIA = 46
    /** @hide Activate a VPN connection without user intervention.
     */
    private val OP_ACTIVATE_VPN = 47
    /** @hide Access the WallpaperManagerAPI to write wallpapers.
     */
    private val OP_WRITE_WALLPAPER = 48
    /** @hide Received the assist structure from an app.
     */
    private val OP_ASSIST_STRUCTURE = 49
    /** @hide Received a screenshot from assist.
     */
    private val OP_ASSIST_SCREENSHOT = 50
    /** @hide Read the phone state.
     */
    private val OP_READ_PHONE_STATE = 51

    /** @hide Add voicemail messages to the voicemail content provider.
     */
    private val OP_ADD_VOICEMAIL = 52

    /** @hide Access APIs for SIP calling over VOIP or WiFi.
     */
    private val OP_USE_SIP = 53

    /** @hide Intercept outgoing calls.
     */
    private val OP_PROCESS_OUTGOING_CALLS = 54

    /** @hide User the fingerprint API.
     */
    private val OP_USE_FINGERPRINT = 55

    /** @hide Access to body sensors such as heart rate, etc.
     */
    private val OP_BODY_SENSORS = 56

    /** @hide Read previously received cell broadcast messages.
     */
    private val OP_READ_CELL_BROADCASTS = 57
    /** @hide Inject mock location into the system.
     */
    private val OP_MOCK_LOCATION = 58
    /** @hide Read external storage.
     */
    private val OP_READ_EXTERNAL_STORAGE = 59


    /** @hide Write external storage.
     */
    private val OP_WRITE_EXTERNAL_STORAGE = 60

    /** @hide Turned on the screen.
     */
    private val OP_TURN_SCREEN_ON = 61
    /** @hide Get device accounts.
     */
    private val OP_GET_ACCOUNTS = 62

    /** @hide Control whether an application is allowed to run in the background.
     */
    private val OP_RUN_IN_BACKGROUND = 63

    /** @hide
     */
    private val OP_AUDIO_ACCESSIBILITY_VOLUME = 64
    /** @hide Read the phone number.
     */
    private val OP_READ_PHONE_NUMBERS = 65

    /** @hide Request package installs through package installer
     */
    private val OP_REQUEST_INSTALL_PACKAGES = 66

    /** @hide Enter picture-in-picture.
     */
    private val OP_PICTURE_IN_PICTURE = 67
    /** @hide Instant app start foreground service.
     */
    private val OP_INSTANT_APP_START_FOREGROUND = 68
    /** @hide Answer incoming phone calls
     */
    private val OP_ANSWER_PHONE_CALLS = 69

    private val _NUM_OP = 70

    /**
     * This optionally maps a permission to an operation.  If there
     * is no permission associated with an operation, it is null.
     */
    private val sOpPerms = arrayOf<String>(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            "",
            android.Manifest.permission.VIBRATE,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.READ_CALL_LOG,
            android.Manifest.permission.WRITE_CALL_LOG,
            android.Manifest.permission.READ_CALENDAR,
            android.Manifest.permission.WRITE_CALENDAR,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            "",
            "", // neighboring cells shares the coarse location perm
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.READ_SMS,
            "", // no permission required for writing sms
            android.Manifest.permission.RECEIVE_SMS,
            "",
            android.Manifest.permission.RECEIVE_MMS,
            android.Manifest.permission.RECEIVE_WAP_PUSH,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.READ_SMS,
            "", // no permission required for writing icc sms
            android.Manifest.permission.WRITE_SETTINGS,
            android.Manifest.permission.SYSTEM_ALERT_WINDOW,
            "",
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "", // no permission for changing bluetooth volume
            android.Manifest.permission.WAKE_LOCK,
            "",
            "", // no permission for high power location monitoring
            android.Manifest.permission.PACKAGE_USAGE_STATS,
            "",
            "",
            "",
            "",
            "",
            "",
            "", // no permission for receiving assist screenshot
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.USE_FINGERPRINT,
            Manifest.permission.BODY_SENSORS,
            "",
            "",
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            "", // no permission for turning the screen on
            Manifest.permission.GET_ACCOUNTS,
            "",
            "", // no permission for changing accessibility volume
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            "", // no permission for entering picture-in-picture on hide
            Manifest.permission.INSTANT_APP_FOREGROUND_SERVICE,
            Manifest.permission.ANSWER_PHONE_CALLS)

    private val sPermStrToOp = HashMap<String, Int>()

    init {
        if (sOpPerms.size != _NUM_OP) {
            throw IllegalStateException("sOpPerms length " + sOpPerms.size
                    + " should be " + _NUM_OP)
        }
        for (i in 0 until _NUM_OP) {
            if (!TextUtils.isEmpty(sOpPerms[i])) {
                sPermStrToOp.put(sOpPerms[i], i)
            }
        }
    }

    fun permissionToOp(perm: String): Int {
        var op = sPermStrToOp.get(perm)
        if (op == null) {
            op = OP_NONE
        }
        return op
    }
}