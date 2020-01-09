package com.example.tarucjob2u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import com.example.tarucjob2u.ui.post_job.job_category
import kotlinx.android.synthetic.main.activity_main.*

import android.widget.TextView

import android.R.drawable.edit_text

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_edit_job.*


import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class EditJobActivity : AppCompatActivity() {

    private lateinit var job: Job
    private lateinit var company: Company

    private lateinit var jobPosition: String
    private lateinit var requirement: String
    private lateinit var minSalary: String
    private lateinit var maxSalary: String
    private lateinit var gender: String
    private val firebaseAuth = FirebaseAuth.getInstance()

    var language = mutableListOf<String>()
    var category = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_job)

        getIncomingIntent()

        editTextJobPosition.setText(job.jobTitle)
        editTextRequirement.setText(job.requirement)
        editTextMinSalary.setText((job.minSalary.toString()))
        editTextMaxSalary.setText((job.maxSalary.toString()))

        if (job.language.contains("English")) checkBoxEnglish.isChecked = true
        if (job.language.contains("Tamil")) checkBoxTamil.isChecked = true
        if (job.language.contains("Malay")) checkBoxMalay.isChecked = true
        if (job.language.contains("Chinese")) checkBoxChinese.isChecked = true

        if (job.tags.contains("Food & Beverage")) checkBoxFnB.isChecked = true
        if (job.tags.contains("Software")) checkBoxSoftware.isChecked = true
        if (job.tags.contains("Sales")) checkBoxSales.isChecked = true
        if (job.tags.contains("Network")) checkBoxNetwork.isChecked = true
        if (job.tags.contains("Account")) checkBoxAccount.isChecked = true
        if (job.tags.contains("Engineering")) checkBoxEngineering.isChecked = true
        if (job.tags.contains("Customer Service")) checkBoxCustomerService.isChecked = true
        if (job.tags.contains("Warehouse")) checkBoxWarehouse.isChecked = true
        if (job.tags.contains("Security")) checkBoxSecurity.isChecked = true
        if (job.tags.contains("Office")) checkBoxOffice.isChecked = true

        if (job.gender.contains("Prefer not to mention")) radioButtonPreferNoGender.isChecked = true
        if (job.gender.contains("Male")) radioButtonMale.isChecked = true
        if (job.gender.contains("Female")) radioButtonFemale.isChecked = true

        buttonCancel.setOnClickListener {
            super.onBackPressed();
        }

        buttonPost.setOnClickListener {
            postJob()
        }

    }

    private fun validateJob(): Boolean {
        jobPosition = editTextJobPosition.text.toString().trim()
        if (jobPosition.isEmpty()) {
            Toast.makeText(applicationContext, "Job position is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        if (checkBoxSales.isChecked) category.add("Sales")
        if (checkBoxOffice.isChecked) category.add("Office")
        if (checkBoxFnB.isChecked) category.add("Food & Beverage")
        if (checkBoxSoftware.isChecked) category.add("Software")
        if (checkBoxNetwork.isChecked) category.add("Network")
        if (checkBoxAccount.isChecked) category.add("Account")
        if (checkBoxEngineering.isChecked) category.add("Engineering")
        if (checkBoxCustomerService.isChecked) category.add("Customer service")
        if (checkBoxWarehouse.isChecked) category.add("Warehouse")
        if (checkBoxSecurity.isChecked) category.add("Security")
        if (category.size == 0) {
            Toast.makeText(this, "Category invalid", Toast.LENGTH_LONG).show()
            return false
        }

        gender = when {
            radioButtonMale.isChecked -> "Male"
            radioButtonFemale.isChecked -> "Female"
            radioButtonPreferNoGender.isChecked -> "Prefer not to mention"
            else -> {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if (checkBoxEnglish.isChecked) language.add("English")
        if (checkBoxChinese.isChecked) language.add("Chinese")
        if (checkBoxMalay.isChecked) language.add("Malay")
        if (checkBoxTamil.isChecked) language.add("Tamil")
        if (language.size == 0) {
            Toast.makeText(this, "Language invalid", Toast.LENGTH_LONG).show()
            return false
        }
        requirement = editTextRequirement.text.toString().trim()
        if (requirement.isEmpty()) {
            Toast.makeText(applicationContext, "Requirement is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        minSalary = editTextMinSalary.text.toString()
        if (minSalary.isEmpty()) {
            Toast.makeText(applicationContext, "Minimum Salary is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        maxSalary = editTextMaxSalary.text.toString()
        if (maxSalary.isEmpty()) {
            Toast.makeText(applicationContext, "Maximum Salary is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }
        val minS: Int = minSalary.toInt()
        val maxS: Int = maxSalary.toInt()

        if (maxS < minS) {
            Toast.makeText(
                applicationContext,
                "Maximum salary cannot smaller than Minimum salary.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

    private fun postJob() {
        if (!validateJob()) return

        val id = firebaseAuth.currentUser!!.uid
        val ref = FirebaseDatabase.getInstance().getReference("Jobs").child(id)

        ref.child("gender").setValue(gender)
        ref.child("tags").setValue(category)
        ref.child("language").setValue(language)
        ref.child("jobTitle").setValue(jobPosition)
        ref.child("maxSalary").setValue(maxSalary)
        ref.child("minSalary").setValue(minSalary)
        ref.child("requirement").setValue(requirement)

    }


    private fun getIncomingIntent() {
        if (intent.hasExtra("job")) {

            job = intent.getParcelableExtra<Job>("job")
            company = intent.getParcelableExtra<Company>("company")

        } else {

            finish()
        }
    }


}