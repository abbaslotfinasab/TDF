package com.utechia.domain.repository

import com.utechia.domain.model.OfficeModel


interface OfficeRepo {

    suspend fun getAllOffice():MutableList<OfficeModel>
}