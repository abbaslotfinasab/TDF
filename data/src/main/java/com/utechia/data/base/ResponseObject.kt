package com.utechia.data.base


interface ResponseObject <out DomainObject : Any?>{

    fun toDomain():DomainObject
}