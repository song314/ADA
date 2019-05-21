package cn.mianyang.song314.ada.framework

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions

abstract class ADABaseActivity<out P : ADABasePresenter<ADABaseViewModel>> : AppCompatActivity(),
    ADABaseView<ADABasePresenter<ADABaseViewModel>> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxPermissions(this)
            .request(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .subscribe()
        lifecycle.addObserver(presenter)
    }

}