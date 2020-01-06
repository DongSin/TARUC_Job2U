package com.example.tarucjob2u

class User(
    val id:String,
    var name:String,
    var email:String,
    val password:String,
    var interest:List<String>,
    var profession:String
) {
    fun setDetails(name: String,email: String,interest: List<String>,profession: String){
        this.name = name
        this.email = email
        this.interest = interest
        this.profession = profession
    }
}