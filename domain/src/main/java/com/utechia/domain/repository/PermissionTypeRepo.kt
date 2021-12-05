package com.utechia.domain.repository

interface PermissionTypeRepo {

    suspend fun getType():MutableList<String>
}