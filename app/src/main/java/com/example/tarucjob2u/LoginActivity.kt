package com.example.tarucjob2u

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = firebaseAuth.currentUser


        if (user != null) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        textViewGoToRegisterCompanyFromLogin.setOnClickListener {
            val intent = Intent(this,RegisterCompanyActivity::class.java)
            startActivity(intent)
        }

        textViewGoRegisterUserFromLogin.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLoginUser.setOnClickListener {
            // Hide the keyboard.
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            login()
        }


    }

    private fun login() {
        val email = editTextEmailLogin.text.toString()
        if(email.isEmpty()){
            Toast.makeText(this,"Name invalid",Toast.LENGTH_LONG).show()
            return
        }
        val password = editTextPasswordLogin.text.toString()
        if(password.isEmpty()){
            Toast.makeText(this,"Password invalid",Toast.LENGTH_LONG).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }else{
                Toast.makeText(this,"Login failed:"+it.exception!!.message,Toast.LENGTH_LONG).show()
                return@addOnCompleteListener
            }
        }

    }
}
