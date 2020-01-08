package com.example.tarucjob2u.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarucjob2u.Job
import com.example.tarucjob2u.JobAdapter
import com.example.tarucjob2u.R
import kotlinx.android.synthetic.main.fragment_special.*

class SpecialFragment:Fragment() {

    private var specialJobList = mutableListOf<Job>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_special, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        var jobAdapter = JobAdapter(requireContext())

        var jobViewModel = ViewModelProviders.of(requireActivity()).get(JobViewModel::class.java)
        jobViewModel.jobList.observe(viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {
                    var filteredList = filterList(it)
                    jobAdapter.setJobList(filteredList)
                }
            })
        recyclerViewSpecial.adapter = jobAdapter
        recyclerViewSpecial.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun filterList(list: List<Job>?): List<Job> {
        //todo bing bong
        return listOf()
    }
}