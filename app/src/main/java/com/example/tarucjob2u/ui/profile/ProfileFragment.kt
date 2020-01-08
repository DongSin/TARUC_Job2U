package com.example.tarucjob2u.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tarucjob2u.R
import com.example.tarucjob2u.ui.post_job.job_category
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button.setOnClickListener{
            var intent = Intent(requireContext(), job_category::class.java)
            startActivity(intent)
        }
    }
}