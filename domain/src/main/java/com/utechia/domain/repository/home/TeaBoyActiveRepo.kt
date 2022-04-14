package com.utechia.domain.repository.home

import com.utechia.domain.model.home.TeaBoyActiveModel

interface TeaBoyActiveRepo {
    suspend fun getActiveList(): MutableList<TeaBoyActiveModel>
}