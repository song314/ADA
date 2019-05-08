package cn.mianyang.song314.libaccessibility

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent



class LibAccessibilityService : AccessibilityService() {

    private val mView = LibAccessibilityView(this)
    private var mCurrentTask: LibAccessibilityTask? = null

    override fun onServiceConnected() {
        mView.create()
    }

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

        Log.i("TTTTTT", "pkgName = " + event.packageName)

        mCurrentTask?.run {
            if (taskState == LibAccessibilityTask.State.BREAKING) {
                Log.i("LibAccessibilityService", " task stop, state = $taskState")
                return@run
            }

            val info = rootInActiveWindow
            info?.run {
                onAccessibilityEvent(this)
                mView.onTaskProcess(mCurrentTask!!)
                //如果任务完成，清空任务
                if (taskState == LibAccessibilityTask.State.FINISHED) {
                    mCurrentTask = null
                    serviceInfo = serviceInfo.apply {
                        packageNames = arrayOf()
                    }
                    Log.i("LibAccessibilityService", " task finished, state = $taskState")
                }
            }

        }
    }

    fun setTask(libAccessibilityTask: LibAccessibilityTask) {
        mCurrentTask = libAccessibilityTask
    }

}