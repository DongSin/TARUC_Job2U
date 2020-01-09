package com.example.tarucjob2u.ui.post_job

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tarucjob2u.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.post_job.*

class job_category : AppCompatActivity() {

    lateinit var editTextJobPosition: EditText
    lateinit var editTextRequirement: EditText
    lateinit var editTextMinSalary: EditText
    lateinit var editTextMaxSalary: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_job)

        editTextJobPosition = findViewById(R.id.editTextJobPosition)
        editTextRequirement = findViewById(R.id.editTextRequirement)
        editTextMinSalary = findViewById(R.id.editTextMinSalary)
        editTextMaxSalary = findViewById(R.id.editTextMaxSalary)

        buttonPost.setOnClickListener {

            postJob()

        }
    }

    private fun postJob() {
        val jobPosition = editTextJobPosition.text.toString().trim()
        val requirement = editTextRequirement.text.toString().trim()
        val minSalary = editTextMinSalary.text.toString()
//        String.format("%.2f", minSalary)
        val maxSalary = editTextMaxSalary.text.toString()
        val gender: String = when {
            radioButtonMale.isChecked -> "Male"
            radioButtonFemale.isChecked -> "Female"
            radioButtonPreferNoGender.isChecked -> "Prefer not to mention"
            else -> {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show()
                return
            }
        }
        val minS: Int = minSalary.toInt()
        val maxS: Int = maxSalary.toInt()
        val language = mutableListOf<String>()
        if (checkBoxEnglish.isChecked) language.add("English")
        if (checkBoxChinese.isChecked) language.add("Chinese")
        if (checkBoxMalay.isChecked) language.add("Malay")
        if (checkBoxTamil.isChecked) language.add("Tamil")

        val interest = mutableListOf<String>()
        if (checkBoxSales.isChecked) interest.add("Sales")
        if (checkBoxOffice.isChecked) interest.add("Office")
        if (checkBoxFnB.isChecked) interest.add("Food & Beverage")
        if (checkBoxSoftware.isChecked) interest.add("Software")
        if (checkBoxNetwork.isChecked) interest.add("Network")
        if (checkBoxAccount.isChecked) interest.add("Account")
        if (checkBoxEngineering.isChecked) interest.add("Engineering")
        if (checkBoxCustomerService.isChecked) interest.add("Customer service")
        if (checkBoxWarehouse.isChecked) interest.add("Warehouse")
        if (checkBoxSecurity.isChecked) interest.add("Security")



        if (jobPosition.isEmpty()) {
            Toast.makeText(applicationContext, "Job position is required.", Toast.LENGTH_LONG).show()
//            editTextJobPosition.error = "Please enter a job position."
            return
        }
        if (requirement.isEmpty()) {
            Toast.makeText(applicationContext, "Requirement is required.", Toast.LENGTH_LONG)
                .show()
//            editTextRequirement.error = "Please enter a requirement."
            return
        }
        if (minSalary.isEmpty()) {
            Toast.makeText(applicationContext, "Minimum Salary is required.", Toast.LENGTH_LONG).show()
//            editTextMinSalary.error = "Minimum Salary is required."
            return
        }
        if (maxSalary.isEmpty()) {
            Toast.makeText(applicationContext, "Maximum Salary is required.", Toast.LENGTH_LONG).show()
//            editTextMaxSalary.error = "Maximum Salary is required."
            return
        }

        if (maxS < minS) {
            Toast.makeText(applicationContext, "Maximum salary cannot smaller than Minimum salary.", Toast.LENGTH_LONG).show()
            return
        }
        val ref = FirebaseDatabase.getInstance().getReference("Jobs")

        val postJobId = ref.push().key

        val newPostJob = Post_job(
            postJobId!!,
            jobPosition,
            requirement,
            minS,
            maxS,
            gender,
            language,
            interest
        )


        ref.child(postJobId).setValue(newPostJob).addOnCompleteListener {
            Toast.makeText(applicationContext, "Save successfully", Toast.LENGTH_LONG).show()
        }

    }
}