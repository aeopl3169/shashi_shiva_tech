package com.shashi.shashishivatech.ui

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.shashi.shashishivatech.ui.fragments.ApplicationsFragment
import com.shashi.shashishivatech.R
import com.shashi.shashishivatech.ui.adapter.ViewPagerAdapter
import com.shashi.shashishivatech.ui.fragments.SettingsFragment
import com.shashi.shashishivatech.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    lateinit var viewModel: ApplicationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tabs)

        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(ApplicationsFragment(), "Application")
        adapter.addFragment(SettingsFragment(), "Settings")

        pager.adapter = adapter

        tab.setupWithViewPager(pager)

    }
}