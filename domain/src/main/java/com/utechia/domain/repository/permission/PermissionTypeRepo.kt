package com.utechia.domain.repository.permission

interface PermissionTypeRepo {

    suspend fun getType():MutableList<String>
}