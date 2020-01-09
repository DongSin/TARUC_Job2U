package com.example.tarucjob2u.ui.report

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tarucjob2u.R
import com.example.tarucjob2u.User
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_job_type_report.*
import kotlinx.android.synthetic.main.fragment_profession_year.*

class ProfessionFragment : Fragment() {
    private var software = TagClass("Software", 0)
    private var food = TagClass("food", 0)
    private var account = TagClass("account", 0)
    private var customerService = TagClass("customerService", 0)
    private var security = TagClass("security", 0)
    private var sales = TagClass("sales", 0)
    private var network = TagClass("network", 0)
    private var engineering = TagClass("engineering", 0)
    private var warehouse = TagClass("warehouse", 0)
    private var office = TagClass("office", 0)
    private var profession = arrayListOf<TagClass>()
    private lateinit var userViewModel: UserViewModel
    private lateinit var userList: List<User>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val root = inflater.inflate(R.layout.fragment_profession_year, container, false)

        return root

    }

    inner class TagClass(var tag: String, var num: Int = 0) {}


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.UserList.observe(viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()) {
                    userList = it
                    for (user in userList) {
                        val tag = TagClass(user.profession, 1)
                        if(profession.size == 0){
                            profession.add(tag)
                        }else {
                            for (pro in profession) {
                                if (pro.tag == tag.tag) {
                                    pro.num += 1
                                    break
                                } else if (profession.indexOf(pro) == profession.size - 1) {
                                    profession.add(tag)
                                }
                            }
                        }

                    }
                    profession.sortedByDescending { it.num }

                    val professionPieChart = view!!.findViewById<PieChart>(R.id.professionPieChart)
                    val pieDataSet = PieDataSet(data(), "")
                    pieDataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))
                    val pieData = PieData(pieDataSet)
                    professionPieChart.data = pieData

                    professionPieChart.invalidate()


//
//
                    val legend: Legend = professionPieChart.getLegend()
                    legend.setEnabled(true)
                    legend.setTextSize(15f)

                    professionPieChart.setData(pieData)
                    professionPieChart.getDescription().setEnabled(false)
                    professionPieChart.setDrawHoleEnabled(false)
                    professionPieChart.setEntryLabelColor(Color.BLACK)
                    pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS!!.toList())
                    professionPieChart.setUsePercentValues(true)
                    pieData.setValueTextColor(Color.BLACK)
                    pieData.setValueTextSize(20f)
                    professionPieChart.invalidate()


                    table()
                }

            }
        )


    }


    private fun table() {

        val jobViewList = listOf(profession1,profession2,profession3,profession4,profession5)
        val noViewList = listOf(require1,require2,require3,require4,require5)

        var i=0
        for(pro in profession) {
            jobViewList[i].setText(pro.tag)
            noViewList[i].setText(pro.num.toString())
            i++
        }
    }

    private fun data(): ArrayList<PieEntry> {
        val datavalue: ArrayList<PieEntry> = ArrayList<PieEntry>()
        var i = 0
        for (pro in profession) {
            datavalue.add(PieEntry(pro.num.toFloat(), pro.tag))
            i++
            if(i == 5) break
        }


        return datavalue
    }
}