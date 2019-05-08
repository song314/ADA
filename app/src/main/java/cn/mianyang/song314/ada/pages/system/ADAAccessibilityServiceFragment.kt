package cn.mianyang.song314.ada.pages.system

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cn.mianyang.song314.ada.R
import cn.mianyang.song314.ada.framework.ADABaseFragment
import cn.mianyang.song314.ada.framework.ADABasePresenter
import cn.mianyang.song314.ada.framework.ADABaseViewModel
import kotlinx.android.synthetic.main.adaaccessibility_fragment.*

class ADAAccessibilityServiceFragment : ADABaseFragment<ADAAccessibilityServicePresenter>() {

    override val layoutRes: Int
        get() = R.layout.adaaccessibility_fragment
    override val viewModel: ADAAccessibilityViewModel
        get() = ViewModelProviders.of(this).get(ADAAccessibilityViewModel::class.java)
    override val presenter: ADAAccessibilityServicePresenter
        get() = ADAAccessibilityServicePresenter(viewModel)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ada_accessibility_text.setOnClickListener {
            presenter.clickBtn()

            try {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            } catch (e: Exception) {
                startActivity(Intent(Settings.ACTION_DREAM_SETTINGS))
                e.printStackTrace()
            }
        }
    }

}
