package cn.mianyang.song314.ada.framework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class ADABaseActivity<out P : ADABasePresenter<ADABaseViewModel>> : AppCompatActivity(),
    ADABaseView<ADABasePresenter<ADABaseViewModel>> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }

}