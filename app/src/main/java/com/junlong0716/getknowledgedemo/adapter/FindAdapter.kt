package com.junlong0716.getknowledgedemo.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.junlong0716.getknowledgedemo.R
import com.junlong0716.getknowledgedemo.entity.FindLayoutTypeBean

/**
 *@author: 巴黎没有摩天轮Li
 *@description: 发现页面 列表整体Adapter
 *@date: Created in 下午1:58 2017/11/8
 *@modified by:
 */
class FindAdapter(data: List<FindLayoutTypeBean>, notifyList: NotifyMusicList) : BaseMultiItemQuickAdapter<FindLayoutTypeBean, BaseViewHolder>(data) {
    private var notifyList: NotifyMusicList

    init {
        addItemType(FindLayoutTypeBean.ITEM_LIVE, R.layout.item_find_live_layout)
        addItemType(FindLayoutTypeBean.ITEM_FREE, R.layout.item_find_free_layout)
        this.notifyList = notifyList
    }

    override fun convert(helper: BaseViewHolder?, item: FindLayoutTypeBean?) {
        when (helper!!.itemViewType) {
            FindLayoutTypeBean.ITEM_FREE -> {
                //设置逻辑思维 状态
                var luojisiwei = helper.getView<LinearLayout>(R.id.ll_ljsw)
                Glide.with(mContext).load(R.mipmap.player).into(helper.getView(R.id.iv_player))
                luojisiwei.setOnClickListener {
                    initPlayStateIcon(item)
                    Toast.makeText(mContext, "luojisiwei", Toast.LENGTH_SHORT).show()
                    Glide.with(mContext).load(R.mipmap.playing).into(helper.getView(R.id.iv_player))
                    //TODO 音乐播放功能实现

                }

                var rvList = helper.getView<RecyclerView>(R.id.rv_list)
                rvList.layoutManager = LinearLayoutManager(mContext)
                val musicAdapter = MusicAdapter(item!!.data[0].freeMusicList, R.layout.item_item_find_free_layout)
                rvList.adapter = musicAdapter
                musicAdapter.onItemClickListener = OnItemClickListener { _, _, position ->
                    initPlayStateIcon(item)
                    item.data[0].freeMusicList[position].isPlaying = true
                    notifyList.notifyFreeMusicList()
                    //TODO 音乐播放功能实现

                }
            }
        }
    }

    /*音乐状态改变  接口回调*/
    interface NotifyMusicList {
        fun notifyFreeMusicList()
    }

    //重置状态图标
    private fun initPlayStateIcon(item: FindLayoutTypeBean?) {
        for (i in item!!.data[0].freeMusicList) {
            i.isPlaying = false
        }
    }
}