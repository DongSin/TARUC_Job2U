package com.example.tarucjob2u

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Job")
data class Job (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val companyId:Int,
    val companyName:String,
    var jobTitle:String,
    var minSalary:Int,
    var maxSalary:Int
){

    fun setDetails(jobTitle: String,minSalary: Int,maxSalary: Int){
        this.jobTitle = jobTitle
        this.minSalary = minSalary
        this.maxSalary = maxSalary
    }

}