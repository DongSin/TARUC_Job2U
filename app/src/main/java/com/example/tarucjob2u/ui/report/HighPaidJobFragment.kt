package com.example.tarucjob2u.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tarucjob2u.Job
import com.example.tarucjob2u.R
import com.example.tarucjob2u.ui.home.JobViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HighPaidJobFragment : Fragment() {


    private lateinit var jobViewModel: JobViewModel
    private lateinit var jobList: List<Job>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_high_paid_jobs_report, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        jobViewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
//        jobViewModel.jobList.observe(viewLifecycleOwner,

        jobViewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
        jobViewModel.jobList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                jobList = it

                jobList.sortedByDescending { it.maxSalary }

                val barChartHighPaid = view!!.findViewById<BarChart>(R.id.barChartHighPaid)
                val barDataSet = BarDataSet(data(), "")
                barDataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))
                barChartHighPaid.getDescription().setEnabled(false)
                val barData = BarData(barDataSet)
                barChartHighPaid.data = barData

                barChartHighPaid.invalidate()

                val companyNames = mutableListOf<String>()
                for (job in jobList) {
                    FirebaseDatabase.getInstance().getReference("Companies").child(job.companyId)
                        .child("name").addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                companyNames.add(p0.getValue(String::class.java)!!)
                                val xAxis: XAxis = barChartHighPaid.xAxis
                                val formater: ValueFormatter = XaxisValue(companyNames) as ValueFormatter
                                xAxis.setValueFormatter(formater)
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
                                xAxis.granularity=1f
                                xAxis.setCenterAxisLabels(true)
                            }

                        })
                }

            }

        })

//        val jobName:ArrayList<String>={jobList[0].companyId
//        val xAxis:XAxis=barChartHighPaid.xAxis
//        xAxis.valueFormatter(XaxisValue(job))


    }

    inner class XaxisValue(
        val mValue: List<String>
    ) : ValueFormatter() {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValue[value.toInt()]
        }

    }

    private fun data(): ArrayList<BarEntry> {
        val datavalue: ArrayList<BarEntry> = ArrayList<BarEntry>()

        datavalue.add(BarEntry(1f, jobList[0].maxSalary.toFloat()))
        datavalue.add(BarEntry(2f, jobList[1].maxSalary.toFloat()))
        datavalue.add(BarEntry(3f, jobList[2].maxSalary.toFloat()))
        datavalue.add(BarEntry(4f, jobList[3].maxSalary.toFloat()))
        datavalue.add(BarEntry(5f, jobList[4].maxSalary.toFloat()))

        return datavalue
    }
}