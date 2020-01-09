package com.example.tarucjob2u.ui.report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tarucjob2u.Job
import com.example.tarucjob2u.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserViewModel(application: Application) : AndroidViewModel(application) {

    //private val repository: JobRepository
    val UserList: MutableLiveData<List<User>> = MutableLiveData()
    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("Users")

    init {

        // create test data
        //createTest()
        val list = mutableListOf<User>()

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                if (dataSnapshot.exists()) {
                    for (i: DataSnapshot in dataSnapshot.children) {

                        val user = i.getValue(User::class.java)
                        list.add(user!!)
                    }
                }
                UserList.value = list
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }






}