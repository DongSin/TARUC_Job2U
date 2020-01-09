package com.example.tarucjob2u.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarucjob2u.Company
import com.example.tarucjob2u.Job
import com.example.tarucjob2u.R
import com.example.tarucjob2u.TagAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_job__detail_.*
import java.text.SimpleDateFormat


class JobDetailActivity : AppCompatActivity() {

    private lateinit var job: Job
    private lateinit var company: Company

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job__detail_)

        getIncomingIntent()
        if (company.imageUri != "") {
            Picasso.with(this).load(company.imageUri).fit().centerCrop().into(imageViewCompany)
        } else {
            imageViewCompany.setImageResource(R.drawable.ic_account_box_black_24dp)
        }

        textViewCompany.text = company.name
        textViewJobTitle.text = job.jobTitle
        textViewSalary.text = "RM" + job.minSalary + " - RM" + job.maxSalary
        textViewGender.text = job.gender
        textViewPostDate.text = SimpleDateFormat("yyyy.MM.dd HH:mm").format(job.date_create)
        textViewRequirement.text = job.requirement
        textViewAddress.text = company.address
        textViewEmail.text = company.email
        textViewPhone.text = company.phone
        textViewDescription.text = company.description

        val tagAdapter = TagAdapter(this)
        tagAdapter.setTagList(job.tags)
        recyclerViewTags.adapter = tagAdapter
        recyclerViewTags.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val languageAdapter = TagAdapter(this)
        languageAdapter.setTagList(job.language)
        recyclerViewLanguage.adapter = languageAdapter
        recyclerViewLanguage.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
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
