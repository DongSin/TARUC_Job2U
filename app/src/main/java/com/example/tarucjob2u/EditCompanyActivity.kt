package com.example.tarucjob2u

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.view.PointerIconCompat
import com.example.tarucjob2u.ui.profile.LoginActivity
import com.example.tarucjob2u.ui.profile.ProfileFragment
import com.example.tarucjob2u.ui.profile.RegisterCompanyActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_company.*
import kotlinx.android.synthetic.main.activity_register_company.*

class EditCompanyActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val database = FirebaseDatabase.getInstance()
    private val companyRef = database.getReference("Companies")
    private val storageRef = FirebaseStorage.getInstance().getReference("Images")
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var name: String
    private lateinit var phone: String

    private lateinit var address: String
    private lateinit var description: String
    val company = Global.loginCompany

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_company)


        if(company!!.imageUri != "") Picasso.with(this).load(company.imageUri).fit().centerCrop().into(imageViewEditCompanyImage)
        else imageViewEditCompanyImage.setImageResource(R.drawable.ic_account_box_black_24dp)
        editTextEditCompanyName.setText(company.name)
        editTextEditCompanyAddress.setText(company.address)
        editTextEditCompanyDescription.setText(company.description)
        editTextEditCompanyPhone.setText(company.phone)


        imageViewEditCompanyImage.setOnClickListener {
            it.startAnimation(
                AnimationUtils.loadAnimation(this,
                    R.anim.anim_click
                ))
            openFileChooser()
        }

        buttonEditCompany.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.anim_click
            ))
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            uploadCompany()
        }
    }

    private fun uploadCompany() {

        if (!validate()) return

        uploadImage()
    }
    private fun uploadImage() {

        //todo delete image
        val mStoreRef =
            storageRef.child("" + System.currentTimeMillis() + "." + getImageExtension(imageUri!!))
        mStoreRef.putFile(imageUri!!).addOnFailureListener {
            Toast.makeText(this, "An error has occurred:" + it.message, Toast.LENGTH_LONG)
                .show()


        }.addOnSuccessListener {

            mStoreRef.downloadUrl.addOnSuccessListener {
                createCompany(it.toString())

            }
            var handler = Handler()
            handler.postDelayed({
                progressBarEditCompany.progress = 0
            }, 5000)
        }.addOnProgressListener {
            var progress = (100.0 * it.bytesTransferred / it.totalByteCount)
            progressBarEditCompany.progress = progress.toInt()
        }


    }
    private fun createCompany(downloadUri: String) {
        val id = firebaseAuth.currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Companies").child(id).child("imageUri").setValue(downloadUri)
        FirebaseDatabase.getInstance().getReference("Companies").child(id).child("name").setValue(name)
        FirebaseDatabase.getInstance().getReference("Companies").child(id).child("phone").setValue(phone)
        FirebaseDatabase.getInstance().getReference("Companies").child(id).child("address").setValue(address)
        FirebaseDatabase.getInstance().getReference("Companies").child(id).child("description").setValue(description)

        Snackbar.make(
            ConstraintLayoutEditCompany,
            "Update completed",
            Snackbar.LENGTH_LONG
        )
            .setAction("Back") {
                this.finish()
            }.show()

    }

    private fun validate(): Boolean {


        name = editTextEditCompanyName.text.toString()
        if (name.isEmpty()) {
            Toast.makeText(this, "Name invalid", Toast.LENGTH_LONG).show()
            return false
        }

        phone = editTextEditCompanyPhone.text.toString()
        if (phone.isEmpty() || !phone.isDigitsOnly()) {
            Toast.makeText(this, "Invalid phone", Toast.LENGTH_LONG).show()
            return false
        }

        address = editTextEditCompanyAddress.text.toString()
        if (address.isEmpty()) {
            Toast.makeText(this, "Address invalid", Toast.LENGTH_LONG).show()
            return false
        }
        description = editTextEditCompanyDescription.text.toString()
        if (description.isEmpty()) {
            Toast.makeText(this, "Description invalid", Toast.LENGTH_LONG).show()
            return false
        }
        if (imageUri == null) {
            createCompany(company!!.imageUri)
            return false
        }
        return true
    }
    private fun getImageExtension(uri: Uri): String {

        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))!!
    }
    private fun openFileChooser() {
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,
            RegisterCompanyActivity.PICK_IMAGE_REQUEST
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            Picasso.with(this).load(imageUri).into(imageViewEditCompanyImage)
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
}
