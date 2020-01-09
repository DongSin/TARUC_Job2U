package com.example.tarucjob2u

import android.os.Parcel
import android.os.Parcelable

class Company(
    val id:String,
    var name:String,
    var address:String,
    var description:String,
    var phone:String,
    var email:String,
    val password:String,
    var jobs:List<Job> = listOf(),
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
        parcel.createTypedArrayList(Job)!!,
        parcel.readString()!!
    ) {
    }

    constructor():this("","","","","","","", listOf<Job>(),""){

    }

    fun setDetails(name: String, address: String, description: String, phone: String, email: String, jobs: List<Job>){
        this.name = name
        this.address = address
        this.description = description
        this.phone = phone
        this.email = email
        this.jobs = jobs
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(description)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeTypedList(jobs)
        parcel.writeString(imageUri)
    }

    override fun describeContents(): Int {
        return 0
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