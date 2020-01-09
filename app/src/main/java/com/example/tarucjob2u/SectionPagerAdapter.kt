package com.example.tarucjob2u

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class SectionPagerAdapter(fragmentManager:FragmentManager):FragmentPagerAdapter(fragmentManager) {

    private var fragmentList:MutableList<Fragment> = mutableListOf()
    private var titleList:MutableList<String> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]


    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    public fun addFragment(fragment:Fragment,title:String){
        fragmentList.add(fragment)
        titleList.add(title)
    }

}