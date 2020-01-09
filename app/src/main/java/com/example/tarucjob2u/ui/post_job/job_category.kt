package com.example.tarucjob2u.ui.post_job

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tarucjob2u.Job
import com.example.tarucjob2u.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_job.*

class job_category : AppCompatActivity() {

    private lateinit var jobPosition: String
    private lateinit var requirement: String
    private lateinit var minSalary: String
    private lateinit var maxSalary: String
    private lateinit var gender: String

    var language = mutableListOf<String>()
    var category = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_job)


        buttonPost.setOnClickListener {
            postJob()
        }

        buttonCancel.setOnClickListener {
            super.onBackPressed();
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
        if (checkBoxCustomerService.isChecked) category.add("Customer Service")
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

        ref.child(postJobId).setValue(newPostJob).addOnCompleteListener {
            Toast.makeText(applicationContext, "Job post successfully", Toast.LENGTH_LONG).show()
        }

    }
}