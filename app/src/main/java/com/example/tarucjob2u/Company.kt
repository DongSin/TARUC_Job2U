package com.example.tarucjob2u

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