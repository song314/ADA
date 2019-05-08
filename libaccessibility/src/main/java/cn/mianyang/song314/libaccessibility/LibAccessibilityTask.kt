package cn.mianyang.song314.libaccessibility

import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.*

class LibAccessibilityTask(
    val taskQueue: LinkedList<Task>,
    val context: LibAccessibilityService
) {

    init {
        context.serviceInfo = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
            packageNames = arrayOf("com.android.settings")
        }
    }

    enum class State {
        FINISHED, INIT, WORKING, BREAKING
    }

    private var currentTask: Task? = null

    var taskState = State.INIT

    fun isFinished(): Boolean {
        return taskQueue.isEmpty()
    }

    fun getCurrentTaskInfo(): String {
        return currentTask?.toString() + ",state:$taskState"
    }

    fun onAccessibilityEvent(event: AccessibilityNodeInfo) {
        if (currentTask != null) {
            currentTask?.progress(event, context)
        } else {
            val task = taskQueue.poll()
            currentTask = task
            task.progress(event, context)
        }

        taskState = if (currentTask!!.isFinished) {
            currentTask = null
            if (taskQueue.isEmpty()) {
                State.FINISHED
            } else {
                State.WORKING
            }
        } else {
            State.WORKING
        }

    }

    abstract class Task {

        var isFinished = false

        abstract fun progress(
            event: AccessibilityNodeInfo,
            context: LibAccessibilityService
        )

        fun findScrollableNode(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            val deque = ArrayDeque<AccessibilityNodeInfo>()
            deque.add(root)
            while (!deque.isEmpty()) {
                val node = deque.removeFirst()
                if (node.actionList.contains(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD)) {
                    return node
                }

                if (node.childCount == 0) {
                    return null
                }

                for (i in 0 until node.childCount) {
                    deque.addLast(node.getChild(i))
                }
            }
            return null
        }

        fun autoClick(info: AccessibilityNodeInfo): Boolean {
            Log.i("TTTTTT", info.toString())
            return if (info.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                true
            } else {
                info.parent?.run {
                    autoClick(this)
                } ?: false
            }
        }

    }

    class TextClickTask(val text: String) : Task() {

        override fun toString(): String {
            return "$text, 状态:$isFinished"
        }

        override fun progress(
            info: AccessibilityNodeInfo,
            context: LibAccessibilityService
        ) {
            val list = info.findAccessibilityNodeInfosByText(text)
            Log.i("TTTTTT", text + " : " + list.size)
            if (list.size == 1) {
                isFinished = autoClick(list[0])
            } else {
                context.performGlobalAction(AccessibilityNodeInfo.ACTION_NEXT_AT_MOVEMENT_GRANULARITY)
                val scrollable = findScrollableNode(context.rootInActiveWindow)
                scrollable?.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD.id)
            }

        }

    }

    class IdClickTask(val id: String) : Task() {

        override fun progress(
            info: AccessibilityNodeInfo,
            context: LibAccessibilityService
        ) {
            val list = info.findAccessibilityNodeInfosByViewId(id)
            Log.i("TTTTTT", id + " : " + list.size)
            if (list.size == 1) {
                if (!list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    isFinished = !list[0].parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                } else {
                    isFinished = true
                }
            } else {

            }

        }

    }
}