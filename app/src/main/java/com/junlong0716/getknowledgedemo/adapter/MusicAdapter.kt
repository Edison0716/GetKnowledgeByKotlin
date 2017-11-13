package com.junlong0716.getknowledgedemo.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.junlong0716.getknowledgedemo.R
import com.junlong0716.getknowledgedemo.entity.MusicBean
import kotlinx.android.synthetic.main.item_item_find_free_layout.view.*

/**
 *@author: 巴黎没有摩天轮Li
 *@description:
 *@date: Created in 下午2:08 2017/11/13
 *@modified by:
 */
class MusicAdapter(data: List<MusicBean>,layoutResId: Int) : BaseQuickAdapter<MusicBean,BaseViewHolder>(layoutResId,data){
    override fun convert(helper: BaseViewHolder?, item: MusicBean?) {
        helper!!.setText(R.id.tv_music_name,item!!.musicName)
        if (item.isPlaying) {
            Glide.with(mContext).load(R.mipmap.playing).into(helper.getView(R.id.iv_player))
        }else{
            Glide.with(mContext).load(R.mipmap.player).into(helper.getView(R.id.iv_player))
        }
    }
}