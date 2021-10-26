package com.utechia.domain.repository

import com.utechia.domain.model.RefreshmentModel

interface TeaBoyRepo {

    fun getRefreshment(category:Int) : MutableList<RefreshmentModel>

    fun like(refreshmentModel: RefreshmentModel)

    fun order(refreshmentModel: MutableList<RefreshmentModel>)

    fun delete(id:Int)

    fun cancel(refreshmentModel: RefreshmentModel)


}