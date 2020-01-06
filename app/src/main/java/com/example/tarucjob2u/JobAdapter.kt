package com.example.tarucjob2u

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class JobAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

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
        if (job.imageUrl != "") {
            Picasso.with(mContext).load(job.imageUrl).fit().centerCrop()
                .into(holder.imageViewCompanyLogo)

        } else {
            holder.imageViewCompanyLogo.setImageResource(R.drawable.ic_account_box_black_24dp)

        }
        holder.textViewCompanyName.text = job.companyName
        holder.textViewJobTitle.text = job.jobTitle
        holder.textViewSalary.text = "RM" + job.minSalary + " - RM" + job.maxSalary
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