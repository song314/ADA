/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package cn.mianyang.song314.ada.utils

import android.content.ComponentName
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.usb.UsbManager
import android.os.RemoteException
import android.util.Log
import java.util.ArrayList
import android.os.UserHandle



object AppUtils {
    private val TAG = "AppUtils"

    fun hasPreferredActivities(pm: PackageManager, packageName: String): Boolean {
        // Get list of preferred activities
        val prefActList = ArrayList<ComponentName>()
        // Intent list cannot be null. so pass empty list
        val intentList = ArrayList<IntentFilter>()
        pm.getPreferredActivities(intentList, prefActList, packageName)
//        Log.d(TAG, "Have " + prefActList.size + " number of activities in preferred list")
        return prefActList.size > 0
    }

//    fun hasUsbDefaults(usbManager: UsbManager, packageName: String): Boolean {
//        try {
//            if (usbManager != null) {
//                return usbManager.hasDefaults(packageName, UserHandle.myUserId())
//            }
//        } catch (e: RemoteException) {
//            Log.e(TAG, "mUsbManager.hasDefaults", e)
//        }
//
//        return false
//    }
}