package com.example.tarucjob2u.ui.post_job

class Post_job(
    val id: String,
    val jobPosition: String,
    val requirement: String,
    val minSalary: Int,
    val maxSalary: Int,
    val gender: String,
    var interest: List<String>,
    var language: List<String>
) {
}