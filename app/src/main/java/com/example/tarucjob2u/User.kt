package com.example.tarucjob2u

data class User(
    val id:Int,
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