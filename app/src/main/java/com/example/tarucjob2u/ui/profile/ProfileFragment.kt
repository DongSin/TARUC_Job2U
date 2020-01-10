package com.example.tarucjob2u.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tarucjob2u.*
import com.example.tarucjob2u.ui.home.CompanyActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
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
        val imageUrl:String = if(Global.loginCompany != null) Global.loginCompany!!.imageUri
        else Global.loginUser!!.imageUri

        if(imageUrl != "") Picasso.with(requireContext()).load(imageUrl).fit().centerCrop().into(imageViewProfileImage)
        else imageViewProfileImage.setImageResource(R.drawable.ic_account_box_black_24dp)

        buttonViewProfile.setOnClickListener {
            if(Global.loginCompany != null){
                val intent = Intent(requireContext(),
                    CompanyActivity::class.java)
                intent.putExtra("company",Global.loginCompany)

                startActivity(intent)
            }else{
                val intent = Intent(requireContext(),
                    UserProfileActivity::class.java)
                startActivity(intent)
            }
        }

        buttonEditProfile.setOnClickListener {
            if(Global.loginCompany != null){
                val intent = Intent(requireContext(),
                    EditCompanyActivity::class.java)

                startActivity(intent)
            }else{
                val intent = Intent(requireContext(),
                    EditUserActivity::class.java)

                startActivity(intent)
            }
        }




        buttonLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}