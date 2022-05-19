package com.utechia.data.repo.reservation

import com.utechia.data.entity.reservation.Hour
import com.utechia.data.entity.reservation.Room
import com.utechia.domain.model.reservation.RoomModel
import com.utechia.domain.repository.reservation.RoomRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepoImpl @Inject constructor(

  /*  private val service: Service,*/

    ): RoomRepo {

    private var room: MutableList<Room> = mutableListOf()
    private var hour: MutableList<Hour> = mutableListOf()

    override suspend fun getRoom(): MutableList<RoomModel> {
        hour.clear()
        hour.add(Hour(0, "8:00", true))
        hour.add(Hour(1, "8:30", true))
        hour.add(Hour(2, "9:00", true))
        hour.add(Hour(3, "9:30", false))
        hour.add(Hour(4, "10:00", false))
        hour.add(Hour(5, "10:30", false))
        hour.add(Hour(6, "11:00", false))
        hour.add(Hour(7, "11:30", false))
        hour.add(Hour(8, "12:00", true))
        hour.add(Hour(9, "12:30", true))
        hour.add(Hour(10, "13:00", true))
        hour.add(Hour(11, "13:30", true))
        hour.add(Hour(12, "14:00", true))
        hour.add(Hour(13, "14:30", true))
        hour.add(Hour(14, "15:00", true))
        hour.add(Hour(15, "15:30", true))
        hour.add(Hour(16, "16:00", true))
        hour.add(Hour(17, "16:30", true))
        hour.add(Hour(18, "17:00", false))

        return emptyList<RoomModel>().toMutableList()
    }
}

