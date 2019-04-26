package cn.mianyang.song314.ada.framework

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class ADABasePresenter<out VM: ADABaseViewModel> : IADALifecycle {

    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
    }


}