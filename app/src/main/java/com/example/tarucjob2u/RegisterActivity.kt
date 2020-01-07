package com.example.tarucjob2u

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var imageUri:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        imageViewImageUpload.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            openFileChooser()
        }

        textViewGoLogin.setOnClickListener{
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))

        }
        textViewGoRegisterCompany.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
        }

        buttonRegister.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            //todo add user
        }
    }

    private fun openFileChooser() {
        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            var imageUri = data.data
            Picasso.with(this).load(imageUri).into(imageViewImageUpload)
        }
    }

    companion object{
        const val PICK_IMAGE_REQUEST = 1
    }
}
