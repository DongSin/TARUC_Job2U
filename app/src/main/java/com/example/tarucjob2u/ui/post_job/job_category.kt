package com.example.tarucjob2u.ui.post_job

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tarucjob2u.Job
import com.example.tarucjob2u.MainActivity
import com.example.tarucjob2u.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.post_job.*

class job_category : AppCompatActivity() {

    private lateinit var jobPosition: String
    private lateinit var requirement: String
    private lateinit var minSalary: String
    private lateinit var maxSalary: String
    private lateinit var gender: String
    private var language = mutableListOf<String>()
    private var category = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_job)


        buttonPost.setOnClickListener {
            postJob()

            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }, 3000)
        }


        buttonCancel.setOnClickListener {
            super.onBackPressed();
        }
    }

    private fun validateJob(): Boolean {
        jobPosition = createEditTextJobPosition.text.toString().trim()
        if (jobPosition.isEmpty()) {
            Toast.makeText(applicationContext, "Job position is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        if (createCheckBoxSales.isChecked) category.add("Sales")
        if (createCheckBoxOffice.isChecked) category.add("Office")
        if (createCheckBoxFnB.isChecked) category.add("Food & Beverage")
        if (createCheckBoxSoftware.isChecked) category.add("Software")
        if (createCheckBoxNetwork.isChecked) category.add("Network")
        if (createCheckBoxAccount.isChecked) category.add("Account")
        if (createCheckBoxEngineering.isChecked) category.add("Engineering")
        if (createCheckBoxCustomerService.isChecked) category.add("Customer Service")
        if (createCheckBoxWarehouse.isChecked) category.add("Warehouse")
        if (createCheckBoxSecurity.isChecked) category.add("Security")
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

        if (createCheckBoxEnglish.isChecked) language.add("English")
        if (createCheckBoxChinese.isChecked) language.add("Chinese")
        if (createCheckBoxMalay.isChecked) language.add("Malay")
        if (createCheckBoxTamil.isChecked) language.add("Tamil")
        if (language.size == 0) {
            Toast.makeText(this, "Language invalid", Toast.LENGTH_LONG).show()
            return false
        }
        requirement = createEditTextRequirement.text.toString().trim()
        if (requirement.isEmpty()) {
            Toast.makeText(applicationContext, "Requirement is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        minSalary = createEditTextMinSalary.text.toString()
        if (minSalary.isEmpty()) {
            Toast.makeText(applicationContext, "Minimum Salary is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        maxSalary = createEditTextMaxSalary.text.toString()
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

        val user = FirebaseAuth.getInstance().currentUser

        val date_create: Long = System.currentTimeMillis()

        val ref = FirebaseDatabase.getInstance().getReference("Jobs")

        val postJobId = ref.push().key

        val newPostJob = Job(
            postJobId!!,
            user!!.uid,
            jobPosition,
            minSalary.toInt(),
            maxSalary.toInt(),
            gender,
            requirement,
            category,
            date_create,
            language
        )

        ref.child(postJobId).setValue(newPostJob).addOnSuccessListener {
            Snackbar.make(
                scrollPost,
                "Create successfully",
                Snackbar.LENGTH_LONG
            )
                .setAction("Back") {
                    finish()
                }.show()
        }





    }
}