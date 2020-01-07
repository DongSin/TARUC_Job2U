package com.example.tarucjob2u

import android.net.Uri

class User(
    val id:String,
    var name:String,
    var email:String,
    val password:String,
    val gender:String,
    var language:List<String>,
    var interest:List<String>,
    var profession:String,
    val imageUri: String
) {
    fun setDetails(name: String,email: String,interest: List<String>,profession: String,language: List<String>){
        this.name = name
        this.email = email
        this.interest = interest
        this.profession = profession
        this.language = language
    }
}