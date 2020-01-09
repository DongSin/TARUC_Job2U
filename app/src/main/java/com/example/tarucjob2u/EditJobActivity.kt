package com.example.tarucjob2u

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.database.*
import android.widget.Toast
import com.example.tarucjob2u.ui.home.LatestJobFragment
import com.example.tarucjob2u.ui.home.PostedJobsFragment
import com.example.tarucjob2u.ui.post_job.job_category
import kotlinx.android.synthetic.main.activity_edit_job.*
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.concurrent.schedule


class EditJobActivity : AppCompatActivity() {

    private lateinit var job: Job
    private lateinit var company: Company

    private lateinit var jobPosition: String
    private lateinit var requirement: String
    private lateinit var minSalary: String
    private lateinit var maxSalary: String
    private lateinit var gender: String

    private var language = mutableListOf<String>()
    private var category = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_job)

        getIncomingIntent()

        updateTextJobPosition.setText(job.jobTitle)
        updateTextRequirement.setText(job.requirement)
        updateTextMinSalary.setText((job.minSalary.toString()))
        updateTextMaxSalary.setText((job.maxSalary.toString()))

        if (job.language.contains("English")) updateCheckBoxEnglish.isChecked = true
        if (job.language.contains("Tamil")) updateCheckBoxTamil.isChecked = true
        if (job.language.contains("Malay")) updateCheckBoxMalay.isChecked = true
        if (job.language.contains("Chinese")) updateCheckBoxChinese.isChecked = true

        if (job.tags.contains("Food & Beverage")) updateCheckBoxFnB.isChecked = true
        if (job.tags.contains("Software")) updateCheckBoxSoftware.isChecked = true
        if (job.tags.contains("Sales")) updateCheckBoxSales.isChecked = true
        if (job.tags.contains("Network")) updateCheckBoxNetwork.isChecked = true
        if (job.tags.contains("Account")) updateCheckBoxAccount.isChecked = true
        if (job.tags.contains("Engineering")) updateCheckBoxEngineering.isChecked = true
        if (job.tags.contains("Customer Service")) updateCheckBoxCustomerService.isChecked = true
        if (job.tags.contains("Warehouse")) updateCheckBoxWarehouse.isChecked = true
        if (job.tags.contains("Security")) updateCheckBoxSecurity.isChecked = true
        if (job.tags.contains("Office")) updateCheckBoxOffice.isChecked = true

        if (job.gender.contains("Prefer not to mention")) radioButtonPreferNoGender.isChecked = true
        if (job.gender.contains("Male")) radioButtonMale.isChecked = true
        if (job.gender.contains("Female")) radioButtonFemale.isChecked = true

        buttonCancel.setOnClickListener {
            super.onBackPressed();
        }

        buttonPost.setOnClickListener {
            postJob()

//            Timer().schedule(2000) {
//                val intent = Intent(this, MainActivity::class.java)
//                finish()
//                overridePendingTransition(0, 0)
//                startActivity(intent)
//                overridePendingTransition(0, 0)
//
//            }

            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }, 3000)
        }

    }

    private fun validateJob(): Boolean {
        jobPosition = updateTextJobPosition.text.toString().trim()
        if (jobPosition.isEmpty()) {
            Toast.makeText(applicationContext, "Job position is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        if (updateCheckBoxSales.isChecked) category.add("Sales")
        if (updateCheckBoxOffice.isChecked) category.add("Office")
        if (updateCheckBoxFnB.isChecked) category.add("Food & Beverage")
        if (updateCheckBoxSoftware.isChecked) category.add("Software")
        if (updateCheckBoxNetwork.isChecked) category.add("Network")
        if (updateCheckBoxAccount.isChecked) category.add("Account")
        if (updateCheckBoxEngineering.isChecked) category.add("Engineering")
        if (updateCheckBoxCustomerService.isChecked) category.add("Customer Service")
        if (updateCheckBoxWarehouse.isChecked) category.add("Warehouse")
        if (updateCheckBoxSecurity.isChecked) category.add("Security")
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

        if (updateCheckBoxEnglish.isChecked) language.add("English")
        if (updateCheckBoxChinese.isChecked) language.add("Chinese")
        if (updateCheckBoxMalay.isChecked) language.add("Malay")
        if (updateCheckBoxTamil.isChecked) language.add("Tamil")
        if (language.size == 0) {
            Toast.makeText(this, "Language invalid", Toast.LENGTH_LONG).show()
            return false
        }
        requirement = updateTextRequirement.text.toString().trim()
        if (requirement.isEmpty()) {
            Toast.makeText(applicationContext, "Requirement is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        minSalary = updateTextMinSalary.text.toString()
        if (minSalary.isEmpty()) {
            Toast.makeText(applicationContext, "Minimum Salary is required.", Toast.LENGTH_LONG)
                .show()
            return false
        }

        maxSalary = updateTextMaxSalary.text.toString()
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


        val ref = FirebaseDatabase.getInstance().getReference("Jobs").child(job.id)

        ref.child("gender").setValue(gender)
        ref.child("tags").setValue(category)
        ref.child("language").setValue(language)
        ref.child("jobTitle").setValue(jobPosition)
        ref.child("maxSalary").setValue(maxSalary.toInt())
        ref.child("minSalary").setValue(minSalary.toInt())
        ref.child("requirement").setValue(requirement)

        Snackbar.make(
            scrollEdit,
            "Update completed",
            Snackbar.LENGTH_LONG
        )
            .setAction("Back") {
                finish()
            }.show()
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