package com.guagua.live.permissionlibrary.lib

import android.Manifest

object PermissionDangerousStack {
    val CALENDAR: Array<String> by lazy {
        arrayOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR)
    }
    val CAMERA: Array<String> by lazy { arrayOf(Manifest.permission.CAMERA) }
    val CONTACTS: Array<String> by lazy {
        arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.GET_ACCOUNTS)
    }
    val LOCATION: Array<String> by lazy {
        arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
    }
    val MICROPHONE: Array<String> by lazy {
        arrayOf(
                Manifest.permission.RECORD_AUDIO)
    }
    val PHONE: Array<String> by lazy {
        arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.ADD_VOICEMAIL,
                Manifest.permission.USE_SIP,
                Manifest.permission.PROCESS_OUTGOING_CALLS)
    }
    val SENSORS: Array<String> by lazy {
        arrayOf(Manifest.permission.BODY_SENSORS)
    }
    val SMS: Array<String>  by lazy {
        arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_MMS)
    }
    val STORAGE: Array<String> by lazy {
        arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun getPermissionGroup(vararg permissions: String): Array<String> {
        val permissionStr = permissions.contentToString()
        return ArrayList<String>().apply {
            if (permissionStr.contains("CALENDAR"))
                addAll(CALENDAR)
            if (permissionStr.contains("CAMERA"))
                addAll(CAMERA)
            if (permissionStr.contains("CONTACTS") ||
                    permissionStr.contains("GET_ACCOUNTS"))
                addAll(CONTACTS)
            if (permissionStr.contains("ACCESS_FINE_LOCATION") ||
                    permissionStr.contains("ACCESS_COARSE_LOCATION"))
                addAll(LOCATION)
            if (permissionStr.contains("RECORD"))
                addAll(MICROPHONE)
            if (permissionStr.contains("READ_PHONE_STATE") ||
                    permissionStr.contains("CALL_PHONE") ||
                    permissionStr.contains("CALL_LOG") ||
                    permissionStr.contains("ADD_VOICEMAIL") ||
                    permissionStr.contains("SIP") ||
                    permissionStr.contains("OUTGOING"))
                addAll(PHONE)
            if (permissionStr.contains("BODY"))
                addAll(SENSORS)
            if (permissionStr.contains("SEND_SMS") ||
                    permissionStr.contains("RECEIVE_SMS") ||
                    permissionStr.contains("READ_SMS") ||
                    permissionStr.contains("RECEIVE_WAP_PUSH") ||
                    permissionStr.contains("MMS"))
                addAll(SMS)
            if (permissionStr.contains("STORAGE"))
                addAll(STORAGE)
        }.toTypedArray()
    }


}
