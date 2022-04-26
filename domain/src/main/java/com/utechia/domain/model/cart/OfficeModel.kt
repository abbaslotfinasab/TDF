package com.utechia.domain.model.cart


data class OfficeModel (

    val id :Int?,
    val name: String?,
    val isDeleted: Boolean?,
    val locations: MutableList<LocationModel>?

    )