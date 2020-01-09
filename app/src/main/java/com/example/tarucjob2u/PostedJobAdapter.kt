package com.example.tarucjob2u

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tarucjob2u.ui.home.JobDetailActivity
import com.example.tarucjob2u.ui.home.PostedJobsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class PostedJobAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<PostedJobAdapter.JobViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var jobList = emptyList<Job>()
    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = inflater.inflate(R.layout.job_record, parent, false)

        return JobViewHolder(itemView)
    }



    override fun getItemCount(): Int {
        return jobList.size
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobList[position]
        //set details into holder
        val companyRef = FirebaseDatabase.getInstance().getReference("Companies")
        var company:Company
        companyRef.child(job.companyId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("Adapter","An error has occurred:"+p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                company = p0.getValue(Company::class.java)!!
                setHolderDetail(company,job,holder)
            }
        })


    }

    private fun setHolderDetail(company: Company,job: Job,holder: JobViewHolder) {
        if(company.imageUri != "") Picasso.with(mContext).load(company.imageUri).fit().centerCrop().into(holder.imageViewCompanyLogo)
        else holder.imageViewCompanyLogo.setImageResource(R.drawable.ic_account_box_black_24dp)
        holder.textViewCompanyName.text = company.name
        holder.textViewJobTitle.text = job.jobTitle
        holder.textViewSalary.text = "RM" + job.minSalary + " - RM" + job.maxSalary

        holder.itemView.setOnClickListener {
            var intent = Intent(mContext, EditJobActivity::class.java)
            intent.putExtra("job",job)
            intent.putExtra("company",company)
            mContext.startActivity(intent)
        }
    }

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCompanyLogo: ImageView = itemView.findViewById(R.id.imageViewCompanyLogo)
        val textViewCompanyName: TextView = itemView.findViewById(R.id.textViewCompanyName)
        val textViewJobTitle: TextView = itemView.findViewById(R.id.textViewJobTitle)
        val textViewSalary: TextView = itemView.findViewById(R.id.textViewSalary)


    }

    fun setJobList(jobList: List<Job>) {
        this.jobList = jobList
        notifyDataSetChanged()
    }


}