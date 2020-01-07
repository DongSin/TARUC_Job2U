package com.example.tarucjob2u

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.MimeTypeMap
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var imageUri:Uri
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("Users")
    private val storageRef = FirebaseStorage.getInstance().getReference("Images")
    private var imageDownloadUrl:String = "asdf"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        FirebaseAuth.getInstance().signInAnonymously()

        imageViewImageUpload.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            openFileChooser()
        }

        textViewGoLogin.setOnClickListener{
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        textViewGoRegisterCompany.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            val intent = Intent(this,RegisterCompanyActivity::class.java)
            startActivity(intent)
        }

        buttonRegister.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            uploadImage()

        }
    }

    private fun uploadImage() {


        if(imageUri != null){
            val mStoreRef = storageRef.child("" + System.currentTimeMillis() + "." + getImageExtension(imageUri))
            mStoreRef.putFile(imageUri).addOnFailureListener{
                Toast.makeText(this,"An error has occurred:"+it.message,Toast.LENGTH_LONG).show()


            }.addOnSuccessListener{
                Log.d("Debug","in success 1")
                mStoreRef.downloadUrl.addOnSuccessListener {


                    uploadUser(it.toString())

                }
            }.addOnProgressListener {
                //todo add progress bar
            }


        }else{
            Toast.makeText(this,"No image selected",Toast.LENGTH_LONG).show()
            return
        }



    }

    private fun uploadUser(downloadUrl: String) {
        if(downloadUrl != ""){

            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val profession = editTextProfession.text.toString()
            val password = editTextPassword.text.toString()
            var gender:String = when {
                radioButtonMale.isChecked -> "Male"
                radioButtonFemale.isChecked -> "Female"
                radioButtonPreferNoGender.isChecked -> "Prefer not to mention"
                else -> {
                    Toast.makeText(this,"Please select gender",Toast.LENGTH_LONG).show()
                    return
                }
            }
            val language = mutableListOf<String>()
            if(checkBoxEnglish.isChecked) language.add("English")
            if(checkBoxChinese.isChecked) language.add("Chinese")
            if(checkBoxMalay.isChecked) language.add("Malay")
            if(checkBoxTamil.isChecked) language.add("Tamil")

            val interest = mutableListOf<String>()
            if(checkBoxSales.isChecked) interest.add("Sales")
            if(checkBoxOffice.isChecked) interest.add("Office")
            if(checkBoxFnB.isChecked) interest.add("Food & Beverage")
            if(checkBoxSoftware.isChecked) interest.add("Software")
            if(checkBoxNetwork.isChecked) interest.add("Network")
            if(checkBoxAccount.isChecked) interest.add("Account")
            if(checkBoxEngineering.isChecked) interest.add("Engineering")
            if(checkBoxCustomerService.isChecked) interest.add("Customer service")
            if(checkBoxWarehouse.isChecked) interest.add("Warehouse")
            if(checkBoxSecurity.isChecked) interest.add("Security")

            val id = userRef.push().key as String
            val newUser = User(id,name,email,password,gender!!,language,interest,profession,downloadUrl)
            userRef.child(id).setValue(newUser).addOnSuccessListener {
                Snackbar.make(ConstraintLayoutRegister,"Registration completed",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Login") {
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                    }.show()
            }.addOnFailureListener { exception ->
                Toast.makeText(this,"An error has occurred:" + exception.message,Toast.LENGTH_LONG).show()
            }



        }else{
            Toast.makeText(this,"Image upload failed",Toast.LENGTH_LONG).show()
        }
    }


    private fun getImageExtension(uri: Uri):String{

        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))!!
    }

    private fun openFileChooser() {
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            imageUri = data.data!!
            Picasso.with(this).load(imageUri).into(imageViewImageUpload)
        }
    }

    companion object{
        const val PICK_IMAGE_REQUEST = 1
    }
}
