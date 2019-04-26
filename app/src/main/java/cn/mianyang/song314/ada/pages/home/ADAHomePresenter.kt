package cn.mianyang.song314.ada.pages.home

import androidx.lifecycle.LifecycleOwner
import cn.mianyang.song314.ada.framework.ADABasePresenter

class ADAHomePresenter(override val viewModel: ADAHomeViewModel) : ADABasePresenter<ADAHomeViewModel>() {


    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.create()
    }

    fun clickFab() {

    }


}
