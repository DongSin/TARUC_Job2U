package com.example.tarucjob2u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val user = Global.loginUser!!

        if(user.imageUri != "") Picasso.with(this).load(user.imageUri).fit().centerCrop().into(imageViewUserProfile)
        else imageViewUserProfile.setImageResource(R.drawable.ic_account_box_black_24dp)

        textViewUserProfileName.text = user.name
        textViewUserProfileEmail.text = user.email
        textViewUserProfileGender.text = user.gender
        textViewUserProfileProfession.text = user.profession

        val languageAdapter = TagAdapter(this)
        languageAdapter.setTagList(user.language)
        recyclerViewUserProfileLanguage.adapter = languageAdapter
        recyclerViewUserProfileLanguage.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        val interestAdapter = TagAdapter(this)
        interestAdapter.setTagList(user.interest)
        recyclerViewUserProfileInterest.adapter = interestAdapter
        recyclerViewUserProfileInterest.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    }


}
