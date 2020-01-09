package com.example.tarucjob2u.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        textViewPhone.setOnClickListener {
            checkPermission()
        }

        textViewEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(textViewEmail.text.toString()))
            intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback")
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
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textViewPhone.text))
        startActivity(intent)
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
