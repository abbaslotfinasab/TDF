package com.utechia.domain.model.ticket

import com.utechia.domain.model.cart.FloorModel


data class BaseNeedsModel(

    val category: MutableList<CategoryModel>?,
    val ListFloor: MutableList<FloorModel>?,
    val Priority: ArrayList<String>?,

    )
