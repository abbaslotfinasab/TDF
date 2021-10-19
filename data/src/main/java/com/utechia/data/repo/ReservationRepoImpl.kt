package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.Invite
import com.utechia.data.entity.Reservation
import com.utechia.domain.model.ReservationModel
import com.utechia.domain.repository.ReservationRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepoImpl @Inject constructor(

    private val service: Service,

    ): ReservationRepo {

    private var reservation:MutableList<Reservation> = mutableListOf()
    private var invite:MutableList<Invite> = mutableListOf()

    override suspend fun reserve(reservationModel: ReservationModel) {

        reservationModel.invite.map {
            invite.add(
                Invite(
                    it.id,
                    it.name,
                    it.image,
                    it.profession,
                    it.invited
                )
            )
        }
        reservation.add(
            Reservation(
                reservationModel.id,
                reservationModel.title,
                reservationModel.capacity,
                reservationModel.room_id,
                reservationModel.day,
                reservationModel.month,
                reservationModel.year,
                reservationModel.starTime,
                reservationModel.endTime,
                reservationModel.duration,
                reservationModel.description,
                invite
            )
        )
    }

    override suspend fun getAll(): MutableList<ReservationModel> =
        reservation.map { it.toDomain() }.toMutableList()


    override suspend fun get(id: Int): ReservationModel = reservation[id].toDomain()

}