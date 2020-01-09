package com.example.tarucjob2u.ui.report

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tarucjob2u.Job


import com.example.tarucjob2u.R
import com.example.tarucjob2u.ui.home.JobViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_job_type_report.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.job_record.*
import org.w3c.dom.Text

class JobTypeReportFragment:Fragment() {

    private var software = TagClass("Software",0)
    private var food = TagClass("food",0)
    private var account = TagClass("account",0)
    private var customerService = TagClass("customerService",0)
    private var security = TagClass("security",0)
    private var sales = TagClass("sales",0)
    private var network = TagClass("network",0)
    private var engineering = TagClass("engineering",0)
    private var warehouse = TagClass("warehouse",0)
    private var office = TagClass("office",0)
    private var tag = mutableListOf<TagClass>(software,food,account,customerService,security,sales,network,engineering,warehouse,office)
        private lateinit var jobViewModel:JobViewModel
        private lateinit var jobList:List<Job>



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val root = inflater.inflate(R.layout.fragment_job_type_report, container, false)

        return root

    }

    inner class TagClass(var tag:String,var num:Int = 0){}


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        jobViewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
        jobViewModel.jobList.observe(viewLifecycleOwner,
            Observer { it ->
                if(it.isNotEmpty()){
                    jobList = it
                    for( job in jobList){
                        for(tag in job.tags){
                            when(tag){
                                "Sales" -> software.num += 1
                                "Food & Beverage" -> food.num += 1
                                "Network" -> network.num += 1
                                "Engineering" -> engineering.num+=1
                                "Warehouse" -> warehouse.num+=1
                                "Office" -> office.num+=1
                                "Software" -> software.num+=1
                                "Account" -> account.num+=1
                                "Customer Service" -> customerService.num+=1
                                "Security" -> security.num+=1
                            }
                        }

                    }
                    tag.sortByDescending{ it.num}

                    val jobTypePie = view!!.findViewById<PieChart>(R.id.jobTypePie)
                    val pieDataSet = PieDataSet(data(),"")
                    pieDataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))
                    val pieData = PieData(pieDataSet)
                    jobTypePie.data = pieData

                    jobTypePie.invalidate()


//
//
                    val legend:Legend = jobTypePie.getLegend()
                    legend.setEnabled(true)
                    legend.setTextSize(15f)

                    jobTypePie.setData(pieData)
                    jobTypePie.getDescription().setEnabled(false)
                    jobTypePie.setDrawHoleEnabled(false)
                    jobTypePie.setEntryLabelColor(Color.BLACK)
                    pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS!!.toList())
                    jobTypePie.setUsePercentValues(true)
                    pieData.setValueTextColor(Color.BLACK)
                    pieData.setValueTextSize(20f)
                    jobTypePie.invalidate()


                    table()
                }

            }
        )





    }


    private fun table() {
        job1.setText(tag[0].tag)
        no1.setText(tag[0].num.toString())
        job2.setText(tag[1].tag)
        no2.setText(tag[1].num.toString())
        job3.setText(tag[2].tag)
        no3.setText(tag[2].num.toString())
        job4.setText(tag[3].tag)
        no4.setText(tag[3].num.toString())
        job5.setText(tag[4].tag)
        no5.setText(tag[4].num.toString())
    }

    private fun data():ArrayList<PieEntry>{
        val datavalue:ArrayList<PieEntry> = ArrayList<PieEntry>()

        datavalue.add(PieEntry(tag[0].num.toFloat(),tag[0].tag))
        datavalue.add(PieEntry(tag[1].num.toFloat(),tag[1].tag))
        datavalue.add(PieEntry(tag[2].num.toFloat(),tag[2].tag))
        datavalue.add(PieEntry(tag[3].num.toFloat(),tag[3].tag))
        datavalue.add(PieEntry(tag[4].num.toFloat(),tag[4].tag))

        return datavalue
    }
//


}