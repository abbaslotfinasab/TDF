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
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchHomeId(): String? {
        return prefs.getString(USER_ID, null)
    }

    fun fetchFireBaeToken(): String? {
        return prefs.getString("fcm", null)
    }

}
