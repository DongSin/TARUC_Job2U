package com.example.tarucjob2u

import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Company(
    val id:String,
    var name:String,
    var address:String,
    var description:String,
    var phone:String,
    var email:String,
    val password:String,
    val imageUri:String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    constructor():this("","","","","","","",""){

    }

    fun setDetails(name: String, address: String, description: String, phone: String, email: String){
        this.name = name
        this.address = address
        this.description = description
        this.phone = phone
        this.email = email

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(description)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(imageUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getJobs(): List<Job> {
        var jobList = mutableListOf<Job>()

        val jobRef = FirebaseDatabase.getInstance().getReference("Jobs")
        jobRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (i:DataSnapshot in p0.children){
                        val job = i.getValue(Job::class.java)
                        if(job!!.companyId == id) jobList.add(job!!)
                    }
                }
            }

        })

        return jobList
    }

    fun getJobs(list:List<Job>): List<Job> {
        var jobList = mutableListOf<Job>()

        for(job in list){
            if(job.companyId == id) jobList.add(job)
        }

        return jobList.toList()
    }

    companion object CREATOR : Parcelable.Creator<Company> {
        override fun createFromParcel(parcel: Parcel): Company {
            return Company(parcel)
        }

        override fun newArray(size: Int): Array<Company?> {
            return arrayOfNulls(size)
        }
    }
}