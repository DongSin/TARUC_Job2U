package com.example.tarucjob2u

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        textViewCompanyDetailPhone.setOnClickListener {
            checkPermission()
        }

        textViewCompanyDetailEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(textViewCompanyDetailEmail.text.toString()))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textViewCompanyDetailPhone.text))
        startActivity(intent)
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
