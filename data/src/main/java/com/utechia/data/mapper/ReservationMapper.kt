package com.utechia.data.mapper

import com.utechia.data.entity.Reservation
import com.utechia.domain.moodel.ReservationModel
import javax.inject.Inject

class ReservationMapper @Inject constructor() {

    fun toMapper (reservationModel: ReservationModel): Reservation {

        return Reservation(

            reservationModel.id?:0,

            reservationModel.title?:"",

            reservationModel.capacity?:0,

            reservationModel.roomImage?:"",

            reservationModel.room_id?:0,

        reservationModel.day?:"",

        reservationModel.month?:"",

            reservationModel.year?:"",

            reservationModel.time?:"",

            reservationModel.guest?:0,

            reservationModel.duration?:"",

            reservationModel.description?:"",

           /* reservationModel.guess_id*/



            )
    }

    fun inMapper (reservationModel: Reservation): ReservationModel {

        return ReservationModel(

            reservationModel.id?:0,

            reservationModel.title?:"",

            reservationModel.capacity?:0,

            reservationModel.roomImage?:"",

            reservationModel.room_id?:0,

            reservationModel.day?:"",

            reservationModel.month?:"",

            reservationModel.year?:"",

            reservationModel.time?:"",

            reservationModel.guest?:0,

            reservationModel.duration?:"",

            reservationModel.description?:"",

            /* reservationModel.guess_id*/


        )
    }
}