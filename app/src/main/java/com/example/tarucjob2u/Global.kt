package com.example.tarucjob2u

import android.app.Application

class Global:Application() {

    companion object {
        var loginCompany: Company? = null
        var loginUser: User? = null
    }
}