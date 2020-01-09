package com.example.tarucjob2u

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CompanyAdapter internal constructor(context: Context):
RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>(){

    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private var companyList = emptyList<Company>()
    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val itemView = inflater.inflate(R.layout.company_record,parent,false)
        return CompanyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companyList[position]
        if(company.imageUri != ""){
            Picasso.with(mContext).load(company.imageUri).fit().centerCrop().into(holder.imageViewLogo)
        }else{
            holder.imageViewLogo.setImageResource(R.drawable.ic_account_box_black_24dp)
        }
        holder.textViewName.text = company.name
        holder.textViewCompanyEmail.text = company.email
        holder.textViewCompanyPhone.text = company.phone


        holder.itemView.setOnClickListener {
            val intent = Intent(mContext,CompanyActivity::class.java)
            intent.putExtra("company",company)
            mContext.startActivity(intent)
        }
    }


    fun setCompanyList(companyList: List<Company>){
        this.companyList = companyList
        notifyDataSetChanged()
    }

    inner class CompanyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageViewLogo: ImageView = itemView.findViewById(R.id.imageViewLogo)
        val textViewName:TextView = itemView.findViewById(R.id.textViewName)
        val textViewCompanyEmail:TextView = itemView.findViewById(R.id.textViewCompanyEmail)
        val textViewCompanyPhone:TextView = itemView.findViewById(R.id.textViewCompanyPhone)
    }
}