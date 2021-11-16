package com.utechia.data.utile


import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("tdf", Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
        const val is_TeaBoy = "isTeaBoy"
    }

    fun saveAuthToken(token: String, userHomeId:String,isTeaBoy:Boolean) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(USER_ID, userHomeId)
        editor.putBoolean(is_TeaBoy, isTeaBoy)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}
