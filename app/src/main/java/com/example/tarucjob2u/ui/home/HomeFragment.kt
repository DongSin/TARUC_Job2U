package com.example.tarucjob2u.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.tarucjob2u.R
import com.example.tarucjob2u.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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

        val user = FirebaseAuth.getInstance().currentUser
        FirebaseDatabase.getInstance().getReference("Companies").child(user!!.uid).addValueEventListener(object:
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(),"An error has occurred:"+p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    pagerAdapter.addFragment(LatestJobFragment(),"Latest")
                    pagerAdapter.addFragment(PostedJobsFragment(),"Posted")
                    pagerAdapter.addFragment(HighPayFragment(),"High Pay")
                    pagerAdapter.addFragment(CompanyFragment(),"Company")

                    viewPager.adapter = pagerAdapter
                }
            }

        })

        FirebaseDatabase.getInstance().getReference("Users").child(user!!.uid).addValueEventListener(object:
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(),"An error has occurred:"+p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    pagerAdapter.addFragment(LatestJobFragment(),"Latest")
                    pagerAdapter.addFragment(SpecialFragment(),"Special")
                    pagerAdapter.addFragment(HighPayFragment(),"HighPay")
                    pagerAdapter.addFragment(CompanyFragment(),"Company")

                    viewPager.adapter = pagerAdapter

                }

            }

        })


    }
}