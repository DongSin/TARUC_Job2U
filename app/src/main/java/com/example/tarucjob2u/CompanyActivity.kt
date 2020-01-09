package com.example.tarucjob2u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarucjob2u.ui.home.JobViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_company.*
import kotlinx.android.synthetic.main.company_record.*

class CompanyActivity : AppCompatActivity() {

    private lateinit var company:Company


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)

        getIncomingIntent()
        if(company.imageUri != "") Picasso.with(this).load(company.imageUri).fit().centerCrop().into(imageViewCompanyDetailLogo)
        else imageViewCompanyDetailLogo.setImageResource(R.drawable.ic_account_box_black_24dp)

        textViewCompanyDetailName.text = company.name
        textViewCompanyDetailDescription.text = company.description
        textViewCompanyDetailAddress.text = company.address
        textViewCompanyDetailPhone.text = company.phone
        textViewCompanyDetailEmail.text = company.email

        val companyJobAdapter = CompanyJobAdapter(this)
        companyJobAdapter.setCompany(company)
        val jobViewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
        jobViewModel.jobList.observe(this,
            Observer {
                if (it.isNotEmpty()) {

                    companyJobAdapter.setJobList(company.getJobs(it))
                }
            })
        companyJobAdapter.setJobList(company.getJobs())
        recyclerViewCompanyDetailJobs.adapter = companyJobAdapter
        recyclerViewCompanyDetailJobs.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


    }

    private fun getIncomingIntent() {
        if(intent.hasExtra("company")){
            company = intent.getParcelableExtra("company")!!

        }else{
            Toast.makeText(this,"Company not found",Toast.LENGTH_LONG).show()
            finish()
        }
    }


}
