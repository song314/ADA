package cn.mianyang.song314.ada.pages.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import cn.mianyang.song314.ada.R
import cn.mianyang.song314.ada.framework.*

class ADAHomeFragment : ADABaseFragment<ADAHomePresenter>() {

    override val layoutRes: Int
        get() = R.layout.ada_fragment_home

    override val viewModel: ADABaseViewModel
        get() = ViewModelProviders.of(this).get(ADAHomeViewModel::class.java)
    override val presenter: ADABasePresenter<ADABaseViewModel>
        get() = ADAHomePresenter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}