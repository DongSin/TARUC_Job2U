package com.example.tarucjob2u

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.style.UpdateLayout
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
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
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("Users")
    private val storageRef = FirebaseStorage.getInstance().getReference("Images")
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var profession: String
    private lateinit var password: String
    private lateinit var gender: String
    private var language = mutableListOf<String>()
    private var interest = mutableListOf<String>()
    private var mUploadTask: StorageTask<UploadTask.TaskSnapshot>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        imageViewImageUpload.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            openFileChooser()
        }

        textViewGoLogin.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        textViewGoRegisterCompany.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            val intent = Intent(this, RegisterCompanyActivity::class.java)
            startActivity(intent)
        }

        buttonRegister.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress) return@setOnClickListener
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_click))
            // Hide the keyboard.
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            uploadUser()

        }
    }

    private fun uploadImage() {



        if (imageUri != null) {
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
                    progressBarRegister.progress = 0
                }, 5000)
            }.addOnProgressListener {
                var progress = (100.0 * it.bytesTransferred / it.totalByteCount)
                progressBarRegister.progress = progress.toInt()
            }


        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_LONG).show()
            return
        }


    }

    private fun validate(): Boolean {
        name = editTextName.text.toString()
        if (name == "") {
            Toast.makeText(this, "Name invalid", Toast.LENGTH_LONG).show()
            return false
        }
        email = editTextEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email invalid", Toast.LENGTH_LONG).show()
            return false
        }
        profession = editTextProfession.text.toString()
        if (profession.isEmpty()) {
            Toast.makeText(this, "Profession invalid", Toast.LENGTH_LONG).show()
            return false
        }
        password = editTextPassword.text.toString()
        if (password.length < 6) {
            Toast.makeText(this, "Password invalid", Toast.LENGTH_LONG).show()
            return false
        }
        gender = when {
            radioButtonMale.isChecked -> "Male"
            radioButtonFemale.isChecked -> "Female"
            radioButtonPreferNoGender.isChecked -> "Prefer not to mention"
            else -> {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show()
                return false
            }
        }

        if (checkBoxEnglish.isChecked) language.add("English")
        if (checkBoxChinese.isChecked) language.add("Chinese")
        if (checkBoxMalay.isChecked) language.add("Malay")
        if (checkBoxTamil.isChecked) language.add("Tamil")
        if (language.size == 0) {
            Toast.makeText(this, "Language invalid", Toast.LENGTH_LONG).show()
            return false
        }


        if (checkBoxSales.isChecked) interest.add("Sales")
        if (checkBoxOffice.isChecked) interest.add("Office")
        if (checkBoxFnB.isChecked) interest.add("Food & Beverage")
        if (checkBoxSoftware.isChecked) interest.add("Software")
        if (checkBoxNetwork.isChecked) interest.add("Network")
        if (checkBoxAccount.isChecked) interest.add("Account")
        if (checkBoxEngineering.isChecked) interest.add("Engineering")
        if (checkBoxCustomerService.isChecked) interest.add("Customer service")
        if (checkBoxWarehouse.isChecked) interest.add("Warehouse")
        if (checkBoxSecurity.isChecked) interest.add("Security")
        if (interest.size == 0) {
            Toast.makeText(this, "Interest invalid", Toast.LENGTH_LONG).show()
            return false
        }
        return true

    }

    private fun uploadUser() {

        if(!validate()) return

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            Log.d("debug", "firebase user complete")
            if (it.isSuccessful) {
                Log.d("debug", "firebase success")
                uploadImage()


            } else {
                Toast.makeText(
                    this,
                    "Create user failed:" + it.exception!!.message,
                    Toast.LENGTH_LONG
                )
                    .show()
                Log.d("debug", "failed:" + it.exception)
            }

        }


    }

    private fun createUser(downloadUrl: String) {
        val id = firebaseAuth.currentUser!!.uid
        val newUser = User(
            id,
            name,
            email,
            password,
            gender!!,
            language,
            interest,
            profession,
            downloadUrl
        )
        userRef.child(id).setValue(newUser).addOnSuccessListener {
            Log.d("debug", "add user success")
            Snackbar.make(
                ConstraintLayoutRegister,
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


    private fun getImageExtension(uri: Uri): String {

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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            Picasso.with(this).load(imageUri).into(imageViewImageUpload)
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
}
