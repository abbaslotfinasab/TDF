package com.utechia.domain.model.cart

import com.utechia.domain.model.cart.LocationModel


data class OfficeModel (

    val id :Int?,
    val name: String?,
    val isDeleted: Boolean?,
    val locations: MutableList<LocationModel>?

    )