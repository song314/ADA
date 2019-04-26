package cn.mianyang.song314.ada.framework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open abstract class ADABaseActivity<out P : ADABasePresenter<ADABaseViewModel>> : AppCompatActivity() {

    abstract val viewModel : ADABaseViewModel
    abstract val presenter : P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }

    override fun onDestroy() {
        super.onDestroy()
//        lifecycle.removeObserver(presenter)
    }
}