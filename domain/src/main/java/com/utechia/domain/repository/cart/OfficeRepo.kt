package com.utechia.domain.repository.cart

import com.utechia.domain.model.cart.OfficeModel


interface OfficeRepo {

    suspend fun getAllOffice():MutableList<OfficeModel>
}