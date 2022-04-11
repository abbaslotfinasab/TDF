package com.utechia.domain.repository.permission

import com.utechia.domain.model.permission.PermissionModel

interface PermissionDetailsRepo {

    suspend fun get(id:Int):MutableList<PermissionModel>
    suspend fun post(type: String, description:String,start:String,end:String):MutableList<PermissionModel>
    suspend fun update(id:Int,status:String):MutableList<PermissionModel>

}