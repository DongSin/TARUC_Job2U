package com.example.tarucjob2u.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarucjob2u.JobAdapter
import com.example.tarucjob2u.R
import kotlinx.android.synthetic.main.fragment_latest_jobs.*

class LatestJobFragment : Fragment() {

    lateinit var jobViewModel: JobViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_latest_jobs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = JobAdapter(requireContext())

        recyclerViewJobs.adapter = adapter
        recyclerViewJobs.layoutManager = LinearLayoutManager(requireContext())


        jobViewModel = ViewModelProviders.of(requireActivity()).get(JobViewModel::class.java)
        jobViewModel.jobList.observe(viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {

                    adapter.setJobList(it)
                }
            })
    }
}