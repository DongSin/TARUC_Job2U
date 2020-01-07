package com.example.tarucjob2u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textViewGoToRegisterCompanyFromLogin.setOnClickListener {
            val intent = Intent(this,RegisterCompanyActivity::class.java)
            startActivity(intent)
        }

        textViewGoRegisterUserFromLogin.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLoginUser.setOnClickListener {
            //todo login user
        }

        buttonLoginCompany.setOnClickListener {
            //todo login company
        }
    }
}
