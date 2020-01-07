package com.example.tarucjob2u

import android.Manifest
import android.app.Application
import android.content.ContentResolver
import android.content.pm.PackageManager
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
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.PermissionChecker.checkSelfPermission


import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.UploadTask.TaskSnapshot
import java.net.URI
import java.util.function.ToIntBiFunction
import kotlin.math.log


//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch

class JobViewModel(application: Application):AndroidViewModel(application) {

    //private val repository: JobRepository
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

    private fun createTest() {
        // Create test data
        val id = jobRef.push().key as String
        val uri = Uri.parse("file:///storage/emulated/0/DCIM/Camera/IMG_20200106_154334.jpg")
        storageRef.child("extra.jpeg")
        val downloadUri = uploadImage(id,uri)
        val tags = listOf("Software","Mobile","Fast Food","International")
        if(downloadUri != null){

            val job = Job(id,"MCD","Mobile Developer",3400,4000,"Male", "3 years of experience, willing to work overtime, high tolerant toward pressure", tags,System.currentTimeMillis(),
                listOf("English","Malay"),downloadUri)
            jobRef.child(id).setValue(job).addOnCompleteListener {
                Log.d("Debug", "job saved")

            }
        }
    }

    private fun uploadImage(id: String,uri:Uri):String {

        var downloadUri = ""
        if (uri != null){
            val fileRef:StorageReference = storageRef.child("$id.jpg")
            fileRef.putFile(uri).addOnSuccessListener {
                Log.d("debug","upload image done")
                downloadUri = it.metadata!!.reference!!.downloadUrl.toString()
            }.addOnFailureListener{
                Log.d("debug","upload failed:" + it.message)

            }

        }
        return downloadUri
    }


}