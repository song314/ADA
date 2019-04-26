package cn.mianyang.song314.ada.pages.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import cn.mianyang.song314.ada.framework.ADABaseViewModel

class ADAHomeViewModel(application: Application) : ADABaseViewModel(application) {

    val openSourceList =  MutableLiveData<MutableList<ADAOpenSource>>()

    fun create() {
        openSourceList.postValue(mutableListOf(ADAOpenSource("www.android.com", "无障碍")))
    }

}

data class ADAOpenSource(val url: String, val name: String)