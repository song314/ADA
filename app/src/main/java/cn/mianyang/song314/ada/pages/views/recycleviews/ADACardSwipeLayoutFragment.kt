package cn.mianyang.song314.ada.pages.views.recycleviews

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import cn.mianyang.song314.ada.R
import cn.mianyang.song314.ada.framework.ADABaseFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.core.widget.ImageViewCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import me.yuqirong.cardswipelayout.OnSwipeListener
import me.yuqirong.cardswipelayout.CardLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.ahamed.multiviewadapter.DataListManager
import com.ahamed.multiviewadapter.ItemBinder
import com.ahamed.multiviewadapter.ItemViewHolder
import com.ahamed.multiviewadapter.RecyclerAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.ada_card_swipe_layout_fragment.*
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback


class ADACardSwipeLayoutFragment : ADABaseFragment<ADACardSwipeLayoutPresenter>() {
    override val layoutRes: Int
        get() = R.layout.ada_card_swipe_layout_fragment
    override val viewModel: ADACardSwipeLayoutViewModel
        get() = ViewModelProviders.of(this).get(ADACardSwipeLayoutViewModel::class.java)
    override val presenter: ADACardSwipeLayoutPresenter
        get() = ADACardSwipeLayoutPresenter(viewModel)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = ada_card_swipe_layout

        val binder = MyBinder()
        val dataList = mutableListOf(
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559037591&di=6737982ea8fd80791fbe077f1cb0c688&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161214%2F99c436b5e1424efab5909f53ae84caf7_th.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558442872075&di=4d2b7c135217d2b56bc95f944f8474e3&imgtype=0&src=http%3A%2F%2Fimage.bitautoimg.com%2Fappimage%2Fmedia%2F20180806%2Fw1265_h696_2dd4832762f64309ba434c5878fcb273.jpeg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3571999284,2268436035&fm=26&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558442872075&di=51af76f5ab0685cd03d610da08cbe7a4&imgtype=0&src=http%3A%2F%2Fnews.inewsweek.cn%2Fdata%2Fupload%2Fimage%2F201704%2F27c2fd0b502dafe7d21c8be43a83d80e.jpg"
        )

        val orgList = dataList.toMutableList()
        val dataManager: DataListManager<String>
        RecyclerAdapter().apply {
            registerBinder(binder)
            dataManager = DataListManager(this)
            addDataManager(dataManager)
            dataManager.addAll(dataList)

            recyclerView.adapter = this
        }

        val cardCallback = CardItemTouchHelperCallback(recyclerView.adapter!!, dataList)
        val touchHelper = ItemTouchHelper(cardCallback)
        val cardLayoutManager = CardLayoutManager(recyclerView, touchHelper)
        recyclerView.layoutManager = cardLayoutManager
        touchHelper.attachToRecyclerView(recyclerView)
        cardCallback.setOnSwipedListener(object : OnSwipeListener<String> {

            override fun onSwiping(viewHolder: RecyclerView.ViewHolder, ratio: Float, direction: Int) {
                /**
                 * will callback when the card are swiping by user
                 * viewHolder : thee viewHolder of swiping card
                 * ratio : the ratio of swiping , you can add some animation by the ratio
                 * direction : CardConfig.SWIPING_LEFT means swiping from left；CardConfig.SWIPING_RIGHT means swiping from right
                 * CardConfig.SWIPING_NONE means not left nor right
                 */
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, t: String, direction: Int) {

                dataManager.remove(t)
                /**
                 * will callback when the card swiped from screen by user
                 * you can also clean animation from the itemview of viewHolder in this method
                 * viewHolder : the viewHolder of swiped cards
                 * t : the data of swiped cards from dataList
                 * direction : CardConfig.SWIPED_LEFT means swiped from left；CardConfig.SWIPED_RIGHT means swiped from right
                 */
            }

            override fun onSwipedClear() {
                /**
                 * will callback when all cards swiped clear
                 * you can load more data
                 */
                Navigation.findNavController(activity!!, R.id.garden_nav_fragment).popBackStack()
            }

        })
    }


    private inner class MyBinder : ItemBinder<String, MyItemViewHolder>() {

        override fun create(inflater: LayoutInflater, parent: ViewGroup?): MyItemViewHolder {
            return MyItemViewHolder(ImageView(inflater.context).apply { })
        }

        override fun canBindData(item: Any?): Boolean {
            return item is String
        }

        override fun bind(holder: MyItemViewHolder, item: String) {
            val tv = holder.itemView as ImageView
            Glide.with(tv).load(item).into(tv)
        }

    }

    private inner class MyItemViewHolder(itemView: View) : ItemViewHolder<String>(itemView) {

    }
}