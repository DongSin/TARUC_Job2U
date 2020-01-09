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
import com.example.tarucjob2u.Company
import com.example.tarucjob2u.JobAdapter
import com.example.tarucjob2u.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_posted_jobs.*

class PostedJobsFragment:Fragment() {

    private lateinit var company:Company

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_posted_jobs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        FirebaseDatabase.getInstance().getReference("Companies").child(user!!.uid).addValueEventListener(object:
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(),"An error has occurred:"+p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    company = p0.getValue(Company::class.java)!!
                    val jobAdapter = JobAdapter(requireContext())
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

        })


    }
}