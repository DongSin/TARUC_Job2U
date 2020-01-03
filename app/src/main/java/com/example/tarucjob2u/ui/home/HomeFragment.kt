package com.example.tarucjob2u.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TabHost
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.tarucjob2u.R
import com.example.tarucjob2u.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout




class HomeFragment : Fragment() {


    private lateinit var viewPager:ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        val root = inflater.inflate(R.layout.fragment_home, container, false)

        viewPager = root.findViewById(R.id.viewPagerHome)


        tabLayout = root.findViewById(R.id.tabs)


        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
    }

    private fun setUpViewPager(viewPager: ViewPager) {
        val pagerAdapter = SectionPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(LatestJobFragment(),"Latest")
        pagerAdapter.addFragment(SpecialFragment(),"Special")
        pagerAdapter.addFragment(LocalFragment(),"Local")
        pagerAdapter.addFragment(OverseasFragment(),"Overseas")

        viewPager.adapter = pagerAdapter
    }
}