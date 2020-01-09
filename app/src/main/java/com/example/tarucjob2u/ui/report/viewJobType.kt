package com.example.tarucjob2u.ui.report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tarucjob2u.Job
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class viewJobType(application: Application): AndroidViewModel(application) {
    val jobList: MutableLiveData<List<Job>> = MutableLiveData()
    val database = FirebaseDatabase.getInstance()
    val jobRef = database.getReference("Jobs")
    val storageRef = FirebaseStorage.getInstance().getReference("Images")

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