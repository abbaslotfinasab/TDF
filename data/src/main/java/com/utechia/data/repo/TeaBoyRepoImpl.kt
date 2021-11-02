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

        refreshment.clear()
        refreshment.add(Refreshment(0,2,"Special Coffee","https://bayanbox.ir/view/3467016343917373981/chamomile-tea-thumb-1-732x549.jpg",false,0,150,"5-10min"))
        refreshment.add(Refreshment(1,2,"Special Tea","https://www.illy.com/content/dam/channels/website/consumer/italy/landing/milk-frother/750x500-Cappuccino.jpg",false,0,150,"5-10min"))

        return when(category){

            3->{
                likeModel.map { it.toDomain() }.toMutableList()
            }

            4->{
                orderModel.map { it.toDomain() }.toMutableList()
            }

            else-> refreshment.map { it.toDomain() }.toMutableList()
        }

    }

    override fun getCard(id: Int): MutableList<RefreshmentModel> {
        card.clear()
        card.add(refreshment[id])
        return card.map { it.toDomain() }.toMutableList()
    }


    override fun like(refreshmentModel: RefreshmentModel) {

        likeModel.add(Refreshment(refreshmentModel.id,refreshmentModel.category,refreshmentModel.name,refreshmentModel.image,refreshmentModel.favorit,refreshmentModel.number,refreshmentModel.calorie,refreshmentModel.time))
    }


    override fun order(refreshmentModel: MutableList<RefreshmentModel>) {

        refreshmentModel.map {

            orderModel.add((Refreshment(it.id,it.category,it.name,it.image,it.favorit,it.number,it.calorie,it.time)))

        }

    }

    override fun delete(id:Int) {

        likeModel.removeAt(id)
    }

    override fun cancel(refreshmentModel: RefreshmentModel) {

        orderModel.remove(Refreshment(refreshmentModel.id,refreshmentModel.category,refreshmentModel.name,refreshmentModel.image,refreshmentModel.favorit,refreshmentModel.number,refreshmentModel.calorie,refreshmentModel.time))

    }

}