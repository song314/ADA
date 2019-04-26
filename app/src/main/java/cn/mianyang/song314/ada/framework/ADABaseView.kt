package cn.mianyang.song314.ada.framework

interface ADABaseView<out P : ADABasePresenter<ADABaseViewModel>> {
    val viewModel : ADABaseViewModel
    val presenter : P
}