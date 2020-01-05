package com.example.tarucjob2u

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(private val context: Context) {

    private val intro = "intro"
    private val user = "user"
    private val appPrefs: SharedPreferences = context.getSharedPreferences(
        "shared",
        Context.MODE_PRIVATE
    )

    fun putIsLogin(loginorout: Boolean) {
        val edit = appPrefs.edit()
        edit.putBoolean(intro, loginorout)
        edit.commit()
    }

    fun getIsLogin(): Boolean {
        return appPrefs.getBoolean(intro, false)
    }

    fun putUser(loginUserId: Int) {
        val edit = appPrefs.edit()
        edit.putInt(user, loginUserId)
        edit.commit()
    }

    fun getUser(): Int? {
        return appPrefs.getInt(user, -1)
    }



}
