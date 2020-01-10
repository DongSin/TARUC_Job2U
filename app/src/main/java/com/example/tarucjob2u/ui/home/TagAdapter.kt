package com.example.tarucjob2u.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarucjob2u.R

class TagAdapter internal constructor(context: Context):RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private var tagList = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val itemView = inflater.inflate(R.layout.tag_record,parent,false)
        return TagViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = tagList[position]

        holder.textViewTag.text = tag
    }

    fun setTagList(tagList:List<String>){
        this.tagList = tagList
        notifyDataSetChanged()
    }

    inner class TagViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val textViewTag:TextView = itemView.findViewById(R.id.textViewTag)

    }
}