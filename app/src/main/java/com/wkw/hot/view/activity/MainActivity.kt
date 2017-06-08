package com.wkw.hot.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.wkw.hot.R
import com.wkw.hot.view.base.ToolbarManager
import com.wkw.hot.view.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy {
        tool_bar
    }
    lateinit var mFragments: MutableList<Fragment>
    lateinit var mTitles: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
        initView()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        mTitles = ArrayList()
        val tabs = resources.getStringArray(R.array.main_tabs)
        for (tab in tabs) {
            mTitles.add(tab)
            mFragments.add(MainFragment.newInstance(tab))
        }
    }

    private fun initView() {
        toolbarTitle = getString(R.string.app_name)
        viewpager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): android.support.v4.app.Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }

            override fun getPageTitle(position: Int): CharSequence {
                return mTitles[position]
            }

        }
        tab_layout.setupWithViewPager(viewpager)
    }

}
