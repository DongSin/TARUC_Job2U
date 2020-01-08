package com.example.tarucjob2u

import android.os.Parcel
import android.os.Parcelable

//import androidx.room.Entity
//import androidx.room.PrimaryKey



class Job(
    val id:String,
    val companyName:String,
    var jobTitle:String,
    var minSalary:Int,
    var maxSalary:Int,
    var gender:String,
    var requirement:String,
    var tags:List<String>,
    val date_create: Long =  System.currentTimeMillis(),
    var language:List<String>,
    val imageUrl:String = ""
):Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readLong(),
        parcel.createStringArrayList()!!,
        parcel.readString()!!
    ) {
    }

    constructor():this("","","",0,0,"","", listOf(),System.currentTimeMillis(), listOf(),""){

    }

    fun setDetails(jobTitle: String,minSalary: Int,maxSalary: Int,gender: String, requirement: String,tags: List<String>,language:List<String>){
        this.jobTitle = jobTitle
        this.minSalary = minSalary
        this.maxSalary = maxSalary
        this.gender = gender
        this.requirement = requirement
        this.tags = tags
        this.language = language
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(companyName)
        parcel.writeString(jobTitle)
        parcel.writeInt(minSalary)
        parcel.writeInt(maxSalary)
        parcel.writeString(gender)
        parcel.writeString(requirement)
        parcel.writeStringList(tags)
        parcel.writeLong(date_create)
        parcel.writeString(imageUrl)
        parcel.writeStringList(language)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Job> {
        override fun createFromParcel(parcel: Parcel): Job {
            return Job(parcel)
        }

        override fun newArray(size: Int): Array<Job?> {
            return arrayOfNulls(size)
        }
    }

}