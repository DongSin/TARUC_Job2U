package com.example.tarucjob2u.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarucjob2u.*
import kotlinx.android.synthetic.main.fragment_posted_jobs.*

class PostedJobsFragment : Fragment() {

    private lateinit var company: Company

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_posted_jobs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        company = Global.loginCompany!!
        val jobAdapter = PostedJobAdapter(requireContext())
        recyclerViewPostedJob.adapter = jobAdapter
        recyclerViewPostedJob.layoutManager = LinearLayoutManager(requireContext())


        val jobViewModel = ViewModelProviders.of(requireActivity()).get(JobViewModel::class.java)
        jobViewModel.jobList.observe(viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {

                    jobAdapter.setJobList(company.getJobs(it))
                }
            })


    }
}