package com.example.tarucjob2u

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.database.DataSnapshot
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.Toast
import java.util.function.ToIntBiFunction


//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch

class JobViewModel(application: Application):AndroidViewModel(application) {

    //private val repository: JobRepository
    val jobList: MutableLiveData<List<Job>> = MutableLiveData()


    init {

        val database = FirebaseDatabase.getInstance()
        val jobRef = database.getReference("Jobs")


//        // Create test data
//        val id = jobRef.push().key as String
//        val tags = listOf("test")
//        val job = Job(id,"TARUC","cleaner",1000,2000, "no requirement",  tags)
//
//        jobRef.child(id).setValue(job).addOnCompleteListener {
//            Log.d("Debug", "job saved")
//
//        }

        val list = mutableListOf<Job>()

        jobRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                if(dataSnapshot.exists()) {
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