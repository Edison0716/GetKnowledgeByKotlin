package com.junlong0716.getknowledgedemo.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.junlong0716.getknowledgedemo.R
import com.junlong0716.getknowledgedemo.entity.ChannelBean

/**
 *@author: 巴黎没有摩天轮Li
 *@description: 频道页 Adapter
 *@date: Created in 下午4:17 2017/11/8
 *@modified by:
 */
class ChannelAdapter(context: Context, data: ArrayList<ChannelBean>, layoutResId: Int) : BaseQuickAdapter<ChannelBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: ChannelBean?) {
        Glide.with(mContext).load(item?.channelIcon).into(helper?.getView(R.id.iv_channel))
        helper?.setText(R.id.tv_channel_name, item?.channelName)
    }
}