package com.example.tarucjob2u.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.example.tarucjob2u.Job


import com.google.firebase.storage.FirebaseStorage


//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch

class JobViewModel(application: Application) : AndroidViewModel(application) {

    //private val repository: JobRepository
    val jobList: MutableLiveData<List<Job>> = MutableLiveData()
    val database = FirebaseDatabase.getInstance()
    val jobRef = database.getReference("Jobs")

    init {

        // create test data
        //createTest()
        val list = mutableListOf<Job>()

        jobRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                if (dataSnapshot.exists()) {
                    for (i: DataSnapshot in dataSnapshot.children) {

                        val job = i.getValue(Job::class.java)
                        list.add(job!!)
                    }
                }
                jobList.value = list
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }






}