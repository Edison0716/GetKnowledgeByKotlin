package com.junlong0716.getknowledgedemo.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *@author: 巴黎没有摩天轮Li
 *@description:
 *@date: Created in 下午2:01 2017/11/8
 *@modified by:
 */
class FindLayoutTypeBean(private var itemType: Int,var data: ArrayList<FindDataBean>) : MultiItemEntity {


    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        val ITEM_LIVE = 1
        val ITEM_FREE = 2
        val ITEM_EVERYDAY_LISTEN_A_BOOK = 3
        val ITEM_SUBSCRIBE = 4
        val ITEM_QUALITY_COURSE = 5
        val ITEM_GUESS_YOUR_LIKE = 6
        val ITEM_HOT_RANK = 7
        val ITEM_STORE = 8
        val ITEM_AD = 9
    }
}