package com.utechia.domain.repository

import com.utechia.domain.model.LoginModel

interface LoginRepo {

    suspend fun getLogin(): LoginModel

    suspend fun verifyLogin(code:String)

}