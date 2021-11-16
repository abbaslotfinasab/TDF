package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.Refreshment
import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.repository.TeaBoyRepo
import javax.inject.Inject

class TeaBoyRepoImpl @Inject constructor(

    private val service: Service

):TeaBoyRepo{

    private val refreshment:MutableList<Refreshment> = mutableListOf()
    private val card:MutableList<Refreshment> = mutableListOf()
    private val likeModel:MutableList<Refreshment> = mutableListOf()
    private val orderModel:MutableList<Refreshment> = mutableListOf()

    override fun getRefreshment(category: Int): MutableList<RefreshmentModel> {

        return refreshment.map { it.toDomain() }.toMutableList()

    }

    override fun getCard(id: Int): MutableList<RefreshmentModel> {

        return refreshment.map { it.toDomain() }.toMutableList()

    }


    override fun like(refreshmentModel: RefreshmentModel) {

    }


    override fun order(refreshmentModel: MutableList<RefreshmentModel>) {


    }

    override fun delete(id:Int) {

        likeModel.removeAt(id)
    }

    override fun cancel(refreshmentModel: RefreshmentModel) {


    }

}