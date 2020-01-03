package com.example.tarucjob2u

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class JobViewModel(application: Application):AndroidViewModel(application) {

    //private val repository: JobRepository
    val jobList: MutableLiveData<List<Job>> = MutableLiveData()


    init {
        var job1 = Job(0,0,"TARUC","Cleaner",1000,1500)
        var job2 = Job(1,0,"KFC","Cashier",1200,2000)
        var job3 = Job(2,0,"MCD","Drive Thru",1500,2300)

        var list = listOf(job1,job2,job3)

        jobList.value = list
    }




}