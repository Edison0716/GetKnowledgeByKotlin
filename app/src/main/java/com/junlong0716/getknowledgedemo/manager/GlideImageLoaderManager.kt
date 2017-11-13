package com.junlong0716.getknowledgedemo.manager

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 *@author: 巴黎没有摩天轮Li
 *@description:
 *@date: Created in 下午2:46 2017/11/8
 *@modified by:
 */
class GlideImageLoaderManager : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context).load(path).into(imageView)
    }
}