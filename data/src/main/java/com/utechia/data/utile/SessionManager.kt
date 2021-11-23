package com.utechia.data.utile


import android.content.Context
import android.content.SharedPreferences
import com.utechia.data.entity.VerifyData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("tdf", Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
        const val is_TeaBoy = "is_teaBoy"
        const val Floor = "floor"
        const val Name = "name"
        const val Job_Title = "job_title"
    }


    fun updateAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun saveAuthToken(verify: VerifyData) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, verify.token)
        editor.putString(USER_ID, verify.userHomeId)
        editor.putBoolean(is_TeaBoy, verify.isTeaBoy!!)
        editor.putString(Floor, verify.isTeaBoy.toString())
        editor.putString(Name, verify.name)
        editor.putString(Job_Title, verify.jobTitle)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchHomeId(): String? {
        return prefs.getString(USER_ID, null)
    }
}
