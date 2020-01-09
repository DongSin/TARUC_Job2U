package com.example.tarucjob2u.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.tarucjob2u.Job
import com.example.tarucjob2u.R
import com.example.tarucjob2u.ui.home.JobViewModel


class HighPaidJobFragment:Fragment() {

    private lateinit var jobViewModel:JobViewModel
    private lateinit var jobList:List<Job>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_high_paid_jobs_report, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        jobViewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
//        jobViewModel.jobList.observe(viewLifecycleOwner,


    }
}