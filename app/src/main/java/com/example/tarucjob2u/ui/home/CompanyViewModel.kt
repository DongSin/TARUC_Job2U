package com.example.tarucjob2u.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tarucjob2u.Company
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CompanyViewModel(application: Application):AndroidViewModel(application) {

    val companyList:MutableLiveData<List<Company>> = MutableLiveData()
    val database = FirebaseDatabase.getInstance()
    val companyRef = database.getReference("Companies")

    init {

        val list = mutableListOf<Company>()

        companyRef.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (i:DataSnapshot in p0.children){
                        val company = i.getValue(Company::class.java)
                        list.add(company!!)
                    }
                }
                companyList.value = list
            }

        })
    }
}