package com.example.tarucjob2u

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarucjob2u.ui.home.JobDetailActivity

class CompanyJobAdapter internal constructor(context:Context):RecyclerView.Adapter<CompanyJobAdapter.CompanyJobViewHolder>() {

    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private var jobList = emptyList<Job>()
    private lateinit var company:Company
    private val mContext = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyJobViewHolder {
        val itemView = inflater.inflate(R.layout.tag_record,parent,false)
        return CompanyJobViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    override fun onBindViewHolder(holder: CompanyJobViewHolder, position: Int) {
        val job = jobList[position]
        holder.textViewTag.text = job.jobTitle
        holder.itemView.setOnClickListener {
            val intent = Intent(mContext,JobDetailActivity::class.java)
            intent.putExtra("job",job)
            intent.putExtra("company",company)
            mContext.startActivity(intent)
        }
    }

    fun setJobList(jobList:List<Job>){
        this.jobList = jobList
        notifyDataSetChanged()
    }

    fun setCompany(company:Company){
        this.company = company
    }

    inner class CompanyJobViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val textViewTag:TextView = itemView.findViewById(R.id.textViewTag)

    }
}