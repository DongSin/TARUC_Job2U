package com.example.tarucjob2u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarucjob2u.ui.home.LatestJobFragment
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_job__detail_.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat


class JobDetailActivity : AppCompatActivity() {

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job__detail_)

        getIncomingIntent()
        if (job.imageUrl != "") {
            Picasso.with(this).load(job.imageUrl).fit().centerCrop().into(imageViewCompany)
        } else {
            imageViewCompany.setImageResource(R.drawable.ic_account_box_black_24dp)
        }

        textViewCompany.text = job.companyName
        textViewJobTitle.text = job.jobTitle
        textViewSalary.text = "RM" + job.minSalary + " - RM" + job.maxSalary
        textViewGender.text = job.gender
        textViewPostDate.text = SimpleDateFormat("yyyy.MM.dd HH:mm").format(job.date_create)
        textViewRequirement.text = job.requirement

        val tagAdapter = TagAdapter(this)
        tagAdapter.setTagList(job.tags)
        recyclerViewTags.adapter = tagAdapter
        recyclerViewTags.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra("job")) {
            Log.d("Debug", "intent success")
            job = intent.getParcelableExtra<Job>("job")



        } else {
            Log.d("Debug", "intent failed")
            finish()
        }
    }
}
