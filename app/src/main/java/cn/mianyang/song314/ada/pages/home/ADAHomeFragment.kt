package cn.mianyang.song314.ada.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.mianyang.song314.ada.R
import cn.mianyang.song314.ada.framework.*
import com.ahamed.multiviewadapter.DataListManager
import com.ahamed.multiviewadapter.ItemBinder
import com.ahamed.multiviewadapter.ItemViewHolder
import com.ahamed.multiviewadapter.RecyclerAdapter
import kotlinx.android.synthetic.main.ada_fragment_home.*
import kotlinx.android.synthetic.main.binder_home_open_source.view.*

class ADAHomeFragment : ADABaseFragment<ADAHomePresenter>() {

    override val layoutRes: Int
        get() = R.layout.ada_fragment_home

    override val viewModel: ADAHomeViewModel
        get() = ViewModelProviders.of(this).get(ADAHomeViewModel::class.java)
    override val presenter: ADABasePresenter<ADABaseViewModel>
        get() = ADAHomePresenter(viewModel)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.openSourceList.observe(this, Observer {
            if (it == null) return@Observer


            val binder = ADAOpenSourceBinder()
            RecyclerAdapter().apply {
                registerBinder(binder)
                val dataManager = DataListManager<ADAOpenSource>(this)
                addDataManager(dataManager)
                dataManager.addAll(it)

                ada_home_rv.adapter = this
            }
            ada_home_rv.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        })
    }
}

private class ADAOpenSourceBinder : ItemBinder<ADAOpenSource, ADAOpenSourceBinder.MyItemViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup?): MyItemViewHolder {
        return MyItemViewHolder(inflater.inflate(R.layout.binder_home_open_source, parent, false))
    }

    override fun canBindData(item: Any?): Boolean {
        return item is ADAOpenSource
    }

    override fun bind(holder: MyItemViewHolder, item: ADAOpenSource) {
        holder.itemView.ada_binder_home_os_text.text = item.name
    }


    private inner class MyItemViewHolder(itemView: View) : ItemViewHolder<ADAOpenSource>(itemView) {

    }
}