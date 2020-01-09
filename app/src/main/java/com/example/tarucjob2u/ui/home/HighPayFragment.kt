package com.example.tarucjob2u.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarucjob2u.Job
import com.example.tarucjob2u.JobAdapter
import com.example.tarucjob2u.R
import com.example.tarucjob2u.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_high_pay.*
import kotlinx.android.synthetic.main.fragment_special.*

class HighPayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_high_pay, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var jobAdapter = JobAdapter(requireContext())

        var jobViewModel =
            ViewModelProviders.of(requireActivity()).get(JobViewModel::class.java)
        jobViewModel.jobList.observe(viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {
                    var sortedJobList = sortList(it.toMutableList())
                    jobAdapter.setJobList(sortedJobList)
                }
            })
        recyclerViewHighPay.adapter = jobAdapter
        recyclerViewHighPay.layoutManager = LinearLayoutManager(requireContext())

    }


    private fun sortList(jobList: MutableList<Job>): List<Job> {
        jobList.sortByDescending { it.maxSalary }
        return jobList
    }
}