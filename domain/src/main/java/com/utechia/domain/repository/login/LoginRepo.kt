package com.utechia.domain.repository.login

import com.utechia.domain.model.login.LoginModel

interface LoginRepo {

    suspend fun getLogin(): LoginModel

}