package com.utechia.data.utile


import android.content.Context
import android.content.SharedPreferences
import com.utechia.data.entity.VerifyData
import com.utechia.domain.enum.MainEnum
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)


    fun saveAuthToken(verify: VerifyData) {
        val editor = prefs.edit()
        editor.putString(MainEnum.Token.main, verify.token)
        editor.putString(MainEnum.HomeId.main, verify.userHomeId)
        editor.apply()
    }

    fun updateAuthToken(token:String) {
        val editor = prefs.edit()
        editor.putString(MainEnum.Token.main, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(MainEnum.Token.main,"")
    }

    fun fetchHomeId(): String? {
        return prefs.getString(MainEnum.HomeId.main, "")
    }

    fun fetchFireBaeToken(): String? {
        return prefs.getString(MainEnum.Fcm.main, "")
    }

}
