package com.example.tarucjob2u

data class Company(
    val id:Int,
    var name:String,
    var address:String,
    var description:String,
    var phone:String,
    var email:String,
    var jobs:List<Job>
) {
    fun setDetails(name: String,address: String,description: String,phone: String,email: String,jobs: List<Job>){
        this.name = name
        this.address = address
        this.description = description
        this.phone = phone
        this.email = email
        this.jobs = jobs
    }
}