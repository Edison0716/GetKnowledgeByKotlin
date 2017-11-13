package com.junlong0716.getknowledgedemo.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *@author: 巴黎没有摩天轮Li
 *@description:
 *@date: Created in 上午10:56 2017/11/8
 *@modified by:
 */
abstract class BaseFragment : Fragment() {
    var isFirst: Boolean = false
    var rootView: View? = null
    var isFragmentVisiable: Boolean = false
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater?.inflate(getLayoutResources(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view!!)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isFragmentVisiable = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isFirst && isFragmentVisiable) {
            onFragmentVisiableChange(true);
            return;
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisiable) {
            onFragmentVisiableChange(false);
            isFragmentVisiable = false;
        }
    }

    open protected fun onFragmentVisiableChange(b: Boolean) {

    }


    abstract fun getLayoutResources(): Int

    abstract fun initView(v: View)
}