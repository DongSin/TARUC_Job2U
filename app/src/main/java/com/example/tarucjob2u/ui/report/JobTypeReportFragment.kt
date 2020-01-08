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


import com.example.tarucjob2u.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_overseas.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.job_record.*
import org.w3c.dom.Text

class JobTypeReportFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_job_type_report, container, false)
        val layout = rootView as ConstraintLayout
        val piechart:PieChart
        piechart=layout.findViewById(R.id.jobTypePie)
        val colorClassArray = mutableListOf(ColorTemplate.COLORFUL_COLORS,ColorTemplate.JOYFUL_COLORS)
        val pieDataSet:PieDataSet = PieDataSet(data(),"")
        val pieData:PieData= PieData(pieDataSet)

        val tableLayout:TableLayout= view!!.findViewById(R.id.jobTypeTable)

        val tr_Row : TableRow=TableRow(this.context)

        val legend:Legend
        legend=piechart.getLegend()
        legend.setEnabled(true)
        legend.setTextSize(15f)



        piechart.getDescription().setEnabled(false)
        piechart.setDrawHoleEnabled(false)
        piechart.setEntryLabelColor(Color.BLACK)
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS!!.toList())
        piechart.setUsePercentValues(true)
        pieData.setValueTextColor(Color.BLACK)
        pieData.setValueTextSize(20f)
        piechart.invalidate()
        piechart.setData(pieData)
        return rootView

    }

    private fun Settable(){
        val job1:TextView=textView6.findViewById(R.id.job1)
        val job2:TextView=textView6.findViewById(R.id.job2)
        val job3:TextView=textView6.findViewById(R.id.job3)
        val job4:TextView=textView6.findViewById(R.id.job4)
        val job5:TextView=textView6.findViewById(R.id.job5)

        val no1:TextView=textView6.findViewById(R.id.available1)
        val no2:TextView=textView6.findViewById(R.id.available2)
        val no3:TextView=textView6.findViewById(R.id.available3)
        val no4:TextView=textView6.findViewById(R.id.available4)
        val no5:TextView=textView6.findViewById(R.id.available5)

        job1.setText("malaysia")
        job2.setText("usa")
        job3.setText("canada")
        job4.setText("uk")
        job5.setText("korea")

        no1.setText("1")
        no1.setText("2")
        no1.setText("3")
        no1.setText("4")
        no1.setText("5")
    }


    private fun data():ArrayList<PieEntry>{
        val datavalue:ArrayList<PieEntry> = ArrayList<PieEntry>()

        datavalue.add(PieEntry(1!!.toFloat(),"malaysia"!!.toString()))
        datavalue.add(PieEntry(2!!.toFloat(),"usa"!!.toString()))
        datavalue.add(PieEntry(3!!.toFloat(),"canada"!!.toString()))
        datavalue.add(PieEntry(4!!.toFloat(),"uk"!!.toString()))
        datavalue.add(PieEntry(5!!.toFloat(),"korea"!!.toString()))
        return datavalue
    }
}