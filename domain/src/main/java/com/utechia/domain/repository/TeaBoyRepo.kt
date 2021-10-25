package com.utechia.domain.repository

import com.utechia.domain.model.RefreshmentModel

interface TeaBoyRepo {

    fun getRefreshment(category:Int) : MutableList<RefreshmentModel>

    fun like(refreshmentModel: RefreshmentModel)

    fun order(refreshmentModel: RefreshmentModel)

    fun delete(refreshmentModel: RefreshmentModel)

    fun cancel(refreshmentModel: RefreshmentModel)


}