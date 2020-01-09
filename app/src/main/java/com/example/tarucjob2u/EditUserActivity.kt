package com.example.tarucjob2u

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.example.tarucjob2u.ui.profile.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.activity_register.*

class EditUserActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("Users")
    private val storageRef = FirebaseStorage.getInstance().getReference("Images")
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var name: String

    private lateinit var profession: String

    private lateinit var gender: String
    private var language = mutableListOf<String>()
    private var interest = mutableListOf<String>()
    private var mUploadTask: StorageTask<UploadTask.TaskSnapshot>? = null
    private val user = Global.loginUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        if(user!!.imageUri != "") Picasso.with(this).load(user!!.imageUri).fit().centerCrop().into(imageViewEditUserImage)
        else imageViewEditUserImage.setImageResource(R.drawable.ic_account_box_black_24dp)

        editTextEditUserName.setText(user.name)
        editTextEditUserProfession.setText(user.profession)
        when(user.gender){
            "Male" -> radioButtonEditMale.isChecked = true
            "Female" -> radioButtonEditFemale.isChecked = true
            "Prefer not to mention" -> radioButtonEditPreferNoGender.isChecked = true
        }
        if(user.language.contains("English")) checkBoxEditEnglish.isChecked = true
        if(user.language.contains("Chinese")) checkBoxEditChinese.isChecked = true
        if(user.language.contains("Malay")) checkBoxEditMalay.isChecked = true
        if(user.language.contains("Tamil")) checkBoxEditTamil.isChecked = true

        if(user.interest.contains("Sales")) checkBoxEditSales.isChecked = true
        if(user.interest.contains("Food & Beverage")) checkBoxEditFnB.isChecked = true
        if(user.interest.contains("Network")) checkBoxEditNetwork.isChecked = true
        if(user.interest.contains("Engineering")) checkBoxEditEngineering.isChecked = true
        if(user.interest.contains("Warehouse")) checkBoxEditWarehouse.isChecked = true
        if(user.interest.contains("Office")) checkBoxEditOffice.isChecked = true
        if(user.interest.contains("Software")) checkBoxEditSoftware.isChecked = true
        if(user.interest.contains("Account")) checkBoxEditAccount.isChecked = true
        if(user.interest.contains("Customer Service")) checkBoxEditCustomerService.isChecked = true
        if(user.interest.contains("Security")) checkBoxEditSecurity.isChecked = true

        imageViewEditUserImage.setOnClickListener {
            it.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.anim_click
                )
            )
            openFileChooser()
        }

        buttonEditUser.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress) return@setOnClickListener
            it.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.anim_click
                )
            )
            // Hide the keyboard.
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            uploadUser()

        }
    }

    private fun uploadUser() {

        if (!validate()) return


        uploadImage()


    }
    private fun uploadImage() {


        val mStoreRef =
            storageRef.child("" + System.currentTimeMillis() + "." + getImageExtension(imageUri!!))
        mUploadTask = mStoreRef.putFile(imageUri!!).addOnFailureListener {
            Toast.makeText(this, "An error has occurred:" + it.message, Toast.LENGTH_LONG)
                .show()


        }.addOnSuccessListener {

            mStoreRef.downloadUrl.addOnSuccessListener {
                createUser(it.toString())

            }
            var handler = Handler()
            handler.postDelayed({
                progressBarEditUser.progress = 0
            }, 5000)
        }.addOnProgressListener {
            var progress = (100.0 * it.bytesTransferred / it.totalByteCount)
            progressBarEditUser.progress = progress.toInt()
        }


    }
    private fun validate(): Boolean {


        name = editTextEditUserName.text.toString()
        if (name == "") {
            Toast.makeText(this, "Name invalid", Toast.LENGTH_LONG).show()
            return false
        }

        profession = editTextEditUserProfession.text.toString()
        if (profession.isEmpty()) {
            Toast.makeText(this, "Profession invalid", Toast.LENGTH_LONG).show()
            return false
        }

        gender = when {
            radioButtonEditMale.isChecked -> "Male"
            radioButtonEditFemale.isChecked -> "Female"
            radioButtonEditPreferNoGender.isChecked -> "Prefer not to mention"
            else -> {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if (checkBoxEditEnglish.isChecked) language.add("English")
        if (checkBoxEditChinese.isChecked) language.add("Chinese")
        if (checkBoxEditMalay.isChecked) language.add("Malay")
        if (checkBoxEditTamil.isChecked) language.add("Tamil")
        if (language.size == 0) {
            Toast.makeText(this, "Language invalid", Toast.LENGTH_LONG).show()
            return false
        }


        if (checkBoxEditSales.isChecked) interest.add("Sales")
        if (checkBoxEditOffice.isChecked) interest.add("Office")
        if (checkBoxEditFnB.isChecked) interest.add("Food & Beverage")
        if (checkBoxEditSoftware.isChecked) interest.add("Software")
        if (checkBoxEditNetwork.isChecked) interest.add("Network")
        if (checkBoxEditAccount.isChecked) interest.add("Account")
        if (checkBoxEditEngineering.isChecked) interest.add("Engineering")
        if (checkBoxEditCustomerService.isChecked) interest.add("Customer service")
        if (checkBoxEditWarehouse.isChecked) interest.add("Warehouse")
        if (checkBoxEditSecurity.isChecked) interest.add("Security")
        if (interest.size == 0) {
            Toast.makeText(this, "Interest invalid", Toast.LENGTH_LONG).show()
            return false
        }

        if (imageUri == null) {
            createUser(user!!.imageUri)
            return false
        }

        return true

    }
    private fun createUser(downloadUrl: String) {
        val id = firebaseAuth.currentUser!!.uid
        val ref = FirebaseDatabase.getInstance().getReference("Users").child(id)
        ref.child("gender").setValue(gender)
        ref.child("imageUri").setValue(downloadUrl)
        ref.child("interest").setValue(interest)
        ref.child("language").setValue(language)
        ref.child("name").setValue(name)
        ref.child("profession").setValue(profession)

        Snackbar.make(
            ConstraintLayoutEditUser,
            "Update completed",
            Snackbar.LENGTH_LONG
        )
            .setAction("Back") {
                this.finish()
            }.show()


    }


    private fun getImageExtension(uri: Uri): String {

        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))!!
    }

    private fun openFileChooser() {
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            intent,
            PICK_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            Picasso.with(this).load(imageUri).into(imageViewEditUserImage)
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
}
