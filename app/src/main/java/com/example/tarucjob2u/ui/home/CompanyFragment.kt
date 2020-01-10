package com.example.tarucjob2u.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarucjob2u.R
import kotlinx.android.synthetic.main.fragment_company.*

class CompanyFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var companyAdapter = CompanyAdapter(requireContext())
        var companyViewModel = ViewModelProviders.of(requireActivity()).get(CompanyViewModel::class.java)
        companyViewModel.companyList.observe(viewLifecycleOwner,
            Observer {
                if(it.isNotEmpty()){
                    companyAdapter.setCompanyList(it)

                }
            })
        recyclerViewCompany.adapter = companyAdapter
        recyclerViewCompany.layoutManager = LinearLayoutManager(requireContext())
    }
}