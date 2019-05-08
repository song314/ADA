package cn.mianyang.song314.libaccessibility

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.hardware.usb.UsbManager
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.FrameLayout
import android.widget.TextView
import java.util.*

class LibAccessibilityView(private val context: LibAccessibilityService) {

    private lateinit var mLayout: FrameLayout

    lateinit var back: View
    lateinit var hide: View
    lateinit var otg: View
    lateinit var connectionDetection: View
    lateinit var libTipInfo: TextView

    var hideView = false

    fun create() {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayout = FrameLayout(context)
        val lp = WindowManager.LayoutParams()
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        lp.format = PixelFormat.TRANSLUCENT
        lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.TOP
        val inflater = LayoutInflater.from(context)
        val rootView = inflater.inflate(R.layout.lib_accessibility_layout, mLayout)
        wm.addView(mLayout, lp)


        //回到拍摄
        back = rootView.findViewById<View>(R.id.lib_accessibility_back)
        back.setOnClickListener {
            val intent = context.packageManager.getLaunchIntentForPackage("us.pinguo.pat360.cameraman")
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }

        connectionDetection = rootView.findViewById<View>(R.id.lib_accessibility_camera_connect_detection)
        connectionDetection.setOnClickListener {

            val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
            Log.i("libAccessibility", "usb:$usbManager")
            Log.i("libAccessibility", "deviceList:" + usbManager.deviceList)
            Log.i("libAccessibility", "accessoryList:" + usbManager.accessoryList)
            Log.i("libAccessibility", "hasUsbHostFeature:" + hasUsbHostFeature(context))

            context.packageManager.systemAvailableFeatures.forEach {
                Log.i("libAccessibility", "systemAvailableFeatures:$it")
            }
        }


        hide = rootView.findViewById<View>(R.id.lib_accessibility_close)
        hide.setOnClickListener {
            hideView = !hideView

            if (hideView) {
                otg.visibility = View.INVISIBLE
                back.visibility = View.INVISIBLE
                connectionDetection.visibility = View.INVISIBLE
            } else {
                otg.visibility = View.VISIBLE
                back.visibility = View.VISIBLE
                connectionDetection.visibility = View.VISIBLE
            }
        }

        otg = rootView.findViewById<View>(R.id.lib_accessibility_otg)
        otg.setOnClickListener {

            libTipInfo.text = "助手正在处理中，期间请不要操作手机。"
            libTipInfo.visibility = View.VISIBLE

            val intent = Intent(Settings.ACTION_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

            context.setTask(LibAccessibilityTask(LinkedList<LibAccessibilityTask.Task>().apply {
                add(LibAccessibilityTask.TextClickTask("其他设置"))
                add(LibAccessibilityTask.TextClickTask("OTG"))
            }, context))
        }

        libTipInfo = rootView.findViewById(R.id.lib_tip_info)
    }

    /**
     * Check the device whether has USB-HOST feature.
     */
    fun hasUsbHostFeature(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_USB_HOST)
    }

    fun onTaskProcess(task: LibAccessibilityTask) {
        if (task.taskState == LibAccessibilityTask.State.FINISHED) {
            libTipInfo.text = ""
            libTipInfo.visibility = View.INVISIBLE
        } else {
            libTipInfo.text = "目标：" + task.getCurrentTaskInfo()
        }

    }


}