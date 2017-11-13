package com.junlong0716.getknowledgedemo.ui


import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.junlong0716.getknowledgedemo.R
import com.junlong0716.getknowledgedemo.adapter.ChannelAdapter
import com.junlong0716.getknowledgedemo.adapter.FindAdapter
import com.junlong0716.getknowledgedemo.adapter.MusicAdapter
import com.junlong0716.getknowledgedemo.decoration.DividerItemDecoration
import com.junlong0716.getknowledgedemo.entity.ChannelBean
import com.junlong0716.getknowledgedemo.entity.FindDataBean
import com.junlong0716.getknowledgedemo.entity.FindLayoutTypeBean
import com.junlong0716.getknowledgedemo.entity.MusicBean
import com.junlong0716.getknowledgedemo.manager.GlideImageLoaderManager
import com.youth.banner.Banner
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.view_search_layout.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit
import android.support.v7.widget.SimpleItemAnimator




/**
 *@author: 巴黎没有摩天轮Li
 *@description:
 *@date: Created in 上午10:55 2017/11/8
 *@modified by:
 */
class FindFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, FindAdapter.NotifyMusicList {
    private var count = 3L
    private var mSubscription: Subscription? = null // Subscription 对象，用于取消订阅关系，防止内存泄露
    private lateinit var layoutTypeList: ArrayList<FindLayoutTypeBean>
    private lateinit var imageList: ArrayList<String>
    private lateinit var channelData: ArrayList<ChannelBean>
    private lateinit var dataInfo: ArrayList<FindDataBean>
    private lateinit var musicList: ArrayList<MusicBean>
    private var overallXScroll = 0//用来记住滑动的距离
    private val height = 440// 滑动开始变色的高,真实项目中此高度是由广告轮播或其他首页view高度决定
    private lateinit var findAdapter: FindAdapter
    private lateinit var llSearch: LinearLayout
    override fun getLayoutResources(): Int {
        return R.layout.fragment_find
    }

    override fun initView(v: View) {
        srl_refresh.setOnRefreshListener(this)
        srl_refresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary))
        channelData = ArrayList<ChannelBean>()
        layoutTypeList = ArrayList<FindLayoutTypeBean>()
        imageList = ArrayList<String>()
        musicList = ArrayList<MusicBean>()
        musicList.add(MusicBean("1", "傅盛：清空和进化", false))
        musicList.add(MusicBean("1", "傅盛：清空和进化", false))
        musicList.add(MusicBean("1", "傅盛：清空和进化", false))
        musicList.add(MusicBean("1", "傅盛：清空和进化", false))

        dataInfo = ArrayList<FindDataBean>()
        dataInfo.add(FindDataBean(musicList))

        layoutTypeList.add(FindLayoutTypeBean(1, dataInfo))
        layoutTypeList.add(FindLayoutTypeBean(2, dataInfo))


        /*data.add(FindBean(3))
        data.add(FindBean(4))
        data.add(FindBean(5))
        data.add(FindBean(6))
        data.add(FindBean(7))
        data.add(FindBean(8))
        data.add(FindBean(9))*/
        channelData.add(ChannelBean("电子书", R.mipmap.icon_ebook))
        channelData.add(ChannelBean("每天听本书", R.mipmap.icon_everyday_book))
        channelData.add(ChannelBean("商城", R.mipmap.icon_shop))
        channelData.add(ChannelBean("系列", R.mipmap.icon_series))
        channelData.add(ChannelBean("全部", R.mipmap.icon_more))

        imageList.add("http://image.woshipm.com/wp-files/2017/11/chanpinjingli.png")
        imageList.add("http://image.woshipm.com/wp-files/2017/11/chanpinjingli.png")
        imageList.add("http://image.woshipm.com/wp-files/2017/11/chanpinjingli.png")
        imageList.add("http://image.woshipm.com/wp-files/2017/11/chanpinjingli.png")

        llSearch = v.findViewById<View>(R.id.ll_search) as LinearLayout

        rv_list.layoutManager = LinearLayoutManager(activity)
        findAdapter = FindAdapter(layoutTypeList,this)
        rv_list.adapter = findAdapter
        (rv_list.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations = false
        //rv_list.addItemDecoration(DividerItemDecoration(activity.applicationContext,DividerItemDecoration.VERTICAL))
        rv_list.addItemDecoration(DividerItemDecoration(activity.applicationContext, LinearLayoutManager.VERTICAL, 30, ContextCompat.getColor(activity, R.color.recyclerview_divider_gray)))

        setTitleBarTransparency()

        initHeaderView(findAdapter)

    }

    private fun initHeaderView(findAdapter: FindAdapter) {
        val headerView: View = LayoutInflater.from(activity).inflate(R.layout.view_find_header_layout, null)
        var banner = headerView.findViewById<View>(R.id.banner) as Banner
        banner.setImageLoader(GlideImageLoaderManager())
        banner.setImages(imageList)
        banner.setDelayTime(5000)
        banner.start()
        val rvChannel = headerView.findViewById<View>(R.id.rv_channel) as RecyclerView
        rvChannel.layoutManager = GridLayoutManager(activity, 5)
        rvChannel.adapter = ChannelAdapter(activity, channelData, R.layout.item_find_channel_layout)
        findAdapter.addHeaderView(headerView)
    }

    //滑动 search bar 背景颜色渐变
    private fun setTitleBarTransparency() {
        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                overallXScroll += dy// 累加y值 解决滑动一半y值为0
                when {
                    overallXScroll <= 0 -> {
                        //设置标题的背景颜色
                        llSearch.setBackgroundColor(Color.argb(0, 255, 255, 255))
                        Glide.with(activity).load(R.mipmap.download_white).into(iv_download)
                        view_divider.setBackgroundColor(Color.argb(0, 229, 229, 229))
                        rl_text_bg.setBackgroundResource(R.drawable.search_bg_white)
                    }
                    overallXScroll in 1..height -> {
                        //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                        val scale = overallXScroll.toFloat() / height
                        val alpha = 255 * scale
                        llSearch.setBackgroundColor(Color.argb(alpha.toInt(), 255, 255, 255))
                    }
                    else -> {
                        llSearch.setBackgroundColor(ContextCompat.getColor(activity, R.color.white))
                        Glide.with(activity).load(R.mipmap.download).into(iv_download)
                        view_divider.setBackgroundColor(Color.argb(255, 229, 229, 229))
                        rl_text_bg.setBackgroundResource(R.drawable.search_bg)
                    }

                }
            }
        })
    }

    override fun onRefresh() {
        Flowable.interval(0, 1, TimeUnit.SECONDS)
                .onBackpressureBuffer()
                .take(count)
                .map { aLong ->
                    count - aLong
                }
                .subscribeOn(Schedulers.io())
//                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Long> {
                    override fun onComplete() {
                        srl_refresh.isRefreshing = false
                        mSubscription?.cancel()//取消订阅，防止内存泄漏
                    }

                    override fun onNext(t: Long?) {
                        Log.d("time", t.toString());
                    }

                    override fun onError(t: Throwable?) {
                        Log.e("error", t.toString())
                    }

                    override fun onSubscribe(s: Subscription?) {
                        mSubscription = s
                        s?.request(Long.MAX_VALUE)//设置请求事件的数量，重要，必须调用
                    }

                })

    }

    override fun notifyFreeMusicList() {
        //Toast.makeText(activity,"notify",Toast.LENGTH_SHORT).show()
        findAdapter.notifyItemChanged(2)
    }
}