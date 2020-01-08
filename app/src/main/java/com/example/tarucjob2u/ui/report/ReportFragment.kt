package com.example.tarucjob2u.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.tarucjob2u.R
import com.example.tarucjob2u.SectionPagerAdapter
import com.example.tarucjob2u.ui.home.LatestJobFragment
import com.example.tarucjob2u.ui.home.LocalFragment
import com.example.tarucjob2u.ui.home.OverseasFragment
import com.example.tarucjob2u.ui.home.SpecialFragment
import com.google.android.material.tabs.TabLayout

class ReportFragment:Fragment() {

    private lateinit var viewPager: ViewPager
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
        pagerAdapter.addFragment(HighPaidJobFragment(),"High Paid ")
        pagerAdapter.addFragment(JobTypeReportFragment(),"Job Type ")
        pagerAdapter.addFragment(ProfessionFragment(),"Profession ")


        viewPager.adapter = pagerAdapter
    }
}