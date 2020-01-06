package com.example.tarucjob2u

//import androidx.room.Entity
//import androidx.room.PrimaryKey



class Job (
    val id:String,
    val companyName:String,
    var jobTitle:String,
    var minSalary:Int,
    var maxSalary:Int,
    var requirement:String,
    var tags:List<String>,
    val date_create: Long =  System.currentTimeMillis(),
    val imageUrl:String = ""
){

    constructor():this("","","",0,0,"", listOf()){

    }

    fun setDetails(jobTitle: String,minSalary: Int,maxSalary: Int, requirement: String,tags: List<String>){
        this.jobTitle = jobTitle
        this.minSalary = minSalary
        this.maxSalary = maxSalary
        this.requirement = requirement
        this.tags = tags
    }

}