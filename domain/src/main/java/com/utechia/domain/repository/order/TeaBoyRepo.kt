package com.utechia.domain.repository.order

import com.utechia.domain.model.refreshment.RefreshmentModel

interface TeaBoyRepo {

    fun getRefreshment(category:Int) : MutableList<RefreshmentModel>

    fun getCard(id:Int) : MutableList<RefreshmentModel>

    fun like(refreshmentModel: RefreshmentModel)

    fun order(refreshmentModel: MutableList<RefreshmentModel>)

    fun delete(id:Int)

    fun cancel(refreshmentModel: RefreshmentModel)

}