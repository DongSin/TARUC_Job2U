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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_special.*

class SpecialFragment : Fragment() {

    private var specialJobList = mutableListOf<Job>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_special, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        var user: User
        var interestList: List<String>
        super.onActivityCreated(savedInstanceState)

        val loginUser = FirebaseAuth.getInstance().currentUser
        FirebaseDatabase.getInstance().getReference("Users").child(loginUser!!.uid)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(
                        requireContext(),
                        "An error has occurred:" + p0.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        user = p0.getValue(User::class.java)!!
                        interestList = user.interest
                        var jobAdapter = JobAdapter(requireContext())

                        var jobViewModel =
                            ViewModelProviders.of(requireActivity()).get(JobViewModel::class.java)
                        jobViewModel.jobList.observe(viewLifecycleOwner,
                            Observer {
                                if (it.isNotEmpty()) {
                                    var filteredList = filterList(it.toMutableList(), interestList)
                                    jobAdapter.setJobList(filteredList)
                                }
                            })
                        recyclerViewSpecial.adapter = jobAdapter
                        recyclerViewSpecial.layoutManager = LinearLayoutManager(requireContext())
                    }

                }

            })


    }


    private fun filterList(list: MutableList<Job>?, interestList: List<String>): List<Job> {
        //todo filter the list based on user interest
        var resultList = mutableListOf<Job>()
        for (job in list!!) {
            for (interest in interestList) {
                if (job.tags.contains(interest)){
                    resultList.add(job)
                    break
                }
            }
        }
        return resultList.toList()
    }
}