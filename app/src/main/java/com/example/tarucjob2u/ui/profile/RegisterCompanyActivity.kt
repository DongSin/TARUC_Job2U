package com.example.tarucjob2u.ui.profile

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
import com.example.tarucjob2u.Company
import com.example.tarucjob2u.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register_company.*

class RegisterCompanyActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val database = FirebaseDatabase.getInstance()
    private val companyRef = database.getReference("Companies")
    private val storageRef = FirebaseStorage.getInstance().getReference("Images")
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var password: String
    private lateinit var address: String
    private lateinit var description: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_company)

        imageViewImageUploadCompany.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.anim_click
            ))
            openFileChooser()
        }

        textViewGoLoginFromCompany.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.anim_click
            ))
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        textViewGoRegisterUserFromCompany.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.anim_click
            ))
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonRegisterCompany.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.anim_click
            ))
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            uploadCompany()
        }


    }

    private fun uploadImage() {


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
                progressBarRegisterCompany.progress = 0
            }, 5000)
        }.addOnProgressListener {
            var progress = (100.0 * it.bytesTransferred / it.totalByteCount)
            progressBarRegisterCompany.progress = progress.toInt()
        }


    }

    private fun createCompany(downloadUri: String) {
        val id = firebaseAuth.currentUser!!.uid
        val newCompany = Company(
            id,
            name,
            address,
            description,
            phone,
            email,
            password,
            downloadUri
        )
        companyRef.child(id).setValue(newCompany).addOnSuccessListener {
            Snackbar.make(
                ConstraintLayoutRegisterCompany,
                "Registration completed",
                Snackbar.LENGTH_LONG
            )
                .setAction("Login") {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }.show()
        }.addOnFailureListener { exception ->
            Toast.makeText(
                this,
                "An error has occurred:" + exception.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun validate(): Boolean {

        if (imageUri == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_LONG).show()
            return false
        }

        name = editTextNameCompany.text.toString()
        if (name.isEmpty()) {
            Toast.makeText(this, "Name invalid", Toast.LENGTH_LONG).show()
            return false
        }
        email = editTextEmailCompany.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email invalid", Toast.LENGTH_LONG).show()
            return false
        }
        phone = editTextPhoneCompany.text.toString()
        if (phone.isEmpty() || !phone.isDigitsOnly()) {
            Toast.makeText(this, "Invalid phone", Toast.LENGTH_LONG).show()
            return false
        }
        password = editTextPasswordCompany.text.toString()
        if (password.length < 6) {
            Toast.makeText(this, "Password invalid", Toast.LENGTH_LONG).show()
            return false
        }
        address = editTextAddressCompany.text.toString()
        if (address.isEmpty()) {
            Toast.makeText(this, "Address invalid", Toast.LENGTH_LONG).show()
            return false
        }
        description = editTextDescriptionCompany.text.toString()
        if (description.isEmpty()) {
            Toast.makeText(this, "Description invalid", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun uploadCompany() {

        if (!validate()) return


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                uploadImage()

            } else {
                Toast.makeText(
                    this,
                    "Create company failed:" + it.exception!!.message,
                    Toast.LENGTH_LONG
                )
                    .show()

            }
        }


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
            PICK_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            Picasso.with(this).load(imageUri).into(imageViewImageUploadCompany)
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
}
