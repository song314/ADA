package cn.mianyang.song314.ada.framework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class ADABaseFragment<out P : ADABasePresenter<ADABaseViewModel>> : Fragment(), ADABaseView<ADABasePresenter<ADABaseViewModel>> {

    abstract val layoutRes : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

}