package com.junlong0716.getknowledgedemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.junlong0716.getknowledgedemo.ui.FindFragment
import com.junlong0716.getknowledgedemo.ui.MineFragment
import com.junlong0716.getknowledgedemo.ui.PaidFragment
import com.junlong0716.getknowledgedemo.ui.TodayFragment
import kotlinx.android.synthetic.main.view_bottom_bar.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //init fragment
    private var findFragment: FindFragment? = null
    private var mineFragment: MineFragment? = null
    private var paidFragment: PaidFragment? = null
    private var todayFragment: TodayFragment? = null
    private var currentFragment: String = "find"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initListener()

        initTextViewAndImageView()

        initFragment(savedInstanceState)


    }


    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            //异常情况
            val mFragments: List<Fragment> = supportFragmentManager.fragments
            for (item in mFragments) {
                if (item is FindFragment) {
                    findFragment = item
                }
                if (item is TodayFragment) {
                    todayFragment = item
                }
                if (item is PaidFragment) {
                    paidFragment = item
                }
                if (item is MineFragment) {
                    mineFragment = item
                }
            }
        } else {
            findFragment = FindFragment()
            todayFragment = TodayFragment()
            mineFragment = MineFragment()
            paidFragment = PaidFragment()
            val fragmentTrans = supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.fl_container, paidFragment)
            fragmentTrans.add(R.id.fl_container, findFragment)
            fragmentTrans.add(R.id.fl_container, mineFragment)
            fragmentTrans.add(R.id.fl_container, todayFragment)
            fragmentTrans.commit()
        }
        supportFragmentManager.beginTransaction().show(findFragment)
                .hide(paidFragment)
                .hide(mineFragment)
                .hide(todayFragment)
                .commit()

        iv_find.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.find_sel))
        tv_find.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

    }

    private fun initListener() {
        rl_find.setOnClickListener(this)
        rl_today.setOnClickListener(this)
        rl_mine.setOnClickListener(this)
        rl_paid.setOnClickListener(this)
    }

    private fun initTextViewAndImageView() {
        iv_find.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.find_unsel))
        tv_find.setTextColor(ContextCompat.getColor(this, R.color.text_color))
        iv_mine.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.find_unsel))
        tv_mine.setTextColor(ContextCompat.getColor(this, R.color.text_color))
        iv_today.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.study_unsel))
        tv_today.setTextColor(ContextCompat.getColor(this, R.color.text_color))
        iv_paid.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.find_unsel))
        tv_paid.setTextColor(ContextCompat.getColor(this, R.color.text_color))
        iv_find.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.find_unsel))
    }

    override fun onClick(v: View?) {
        initTextViewAndImageView()
        when (v?.id) {
            R.id.rl_find -> {
                currentFragment = "find"
                iv_find.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.find_sel))
                tv_find.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                supportFragmentManager.beginTransaction().show(findFragment)
                        .hide(paidFragment)
                        .hide(mineFragment)
                        .hide(todayFragment)
                        .commit()

            }
            R.id.rl_today -> {
                currentFragment = "today"
                iv_today.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.study_sel))
                tv_today.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                supportFragmentManager.beginTransaction().show(todayFragment)
                        .hide(paidFragment)
                        .hide(mineFragment)
                        .hide(findFragment)
                        .commit()
            }
            R.id.rl_paid -> {
                currentFragment = "paid"
                iv_paid.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.find_sel))
                tv_paid.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                supportFragmentManager.beginTransaction().show(paidFragment)
                        .hide(findFragment)
                        .hide(mineFragment)
                        .hide(todayFragment)
                        .commit()
            }
            R.id.rl_mine -> {
                currentFragment = "mine"
                iv_mine.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.find_sel))
                tv_mine.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                supportFragmentManager.beginTransaction().show(mineFragment)
                        .hide(paidFragment)
                        .hide(findFragment)
                        .hide(todayFragment)
                        .commit()
            }
        }
    }

}
