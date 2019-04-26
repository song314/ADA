package cn.mianyang.song314.ada.pages.home

import android.appwidget.AppWidgetManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import cn.mianyang.song314.ada.R
import cn.mianyang.song314.ada.framework.ADABaseActivity
import cn.mianyang.song314.ada.framework.ADABaseViewModel

import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.content.Intent
import android.hardware.usb.UsbManager
import android.util.Log
import cn.mianyang.song314.ada.utils.AppUtils


class ADAHomeActivity : ADABaseActivity<ADAHomePresenter>() {

    override val viewModel: ADABaseViewModel
        get() = ViewModelProviders.of(this).get(ADAHomeViewModel::class.java)
    override val presenter: ADAHomePresenter
        get() = ADAHomePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            presenter.clickFab()

            (getSystemService(Context.USB_SERVICE) as UsbManager).run {

            }
            AppWidgetManager.getInstance(this)
            val pm = packageManager
            val intent = Intent(UsbManager.ACTION_USB_DEVICE_ATTACHED)
//            val info = intent.resolveActivityInfo(packageManager, PackageManager.MATCH_ALL)
//            val msg = "当前默认程序的包名:${info?.packageName}"
            pm.queryIntentActivities(intent, PackageManager.MATCH_ALL)?.forEach {
                val msg = "当前默认程序的包名:${it.activityInfo.packageName}, ${AppUtils.hasPreferredActivities(
                    pm,
                    it.activityInfo.packageName
                )}"

                Log.i("aaa", msg)
            }

//            val info = pm.resolveActivity(intent, PackageManager.MATCH_ALL)
//            val msg = "当前默认程序的包名:${info?.activityInfo?.packageName}"
//            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
