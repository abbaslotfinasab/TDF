package com.utechia.domain.repository

import com.utechia.domain.model.PermissionModel

interface PermissionRepo {

    suspend fun getPermission(status:String):MutableList<PermissionModel>
    suspend fun get(id:Int):MutableList<PermissionModel>
    suspend fun post(type: String, description:String,start:String,end:String):MutableList<PermissionModel>
    suspend fun update(id:Int,status:String):MutableList<PermissionModel>
}