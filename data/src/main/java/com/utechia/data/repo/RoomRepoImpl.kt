package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.Hour
import com.utechia.data.entity.Room
import com.utechia.domain.model.RoomModel
import com.utechia.domain.repository.RoomRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepoImpl @Inject constructor(

    private val service: Service,

    ):RoomRepo {

    private var room:MutableList<Room> = mutableListOf()
    private var hour:MutableList<Hour> = mutableListOf()

    override suspend fun getRoom(): MutableList<RoomModel> {
        hour.clear()
        hour.add(Hour(0,"8:00",true))
        hour.add(Hour(1,"8:30",true))
        hour.add(Hour(2,"9:00",true))
        hour.add(Hour(3,"9:30",false))
        hour.add(Hour(4,"10:00",false))
        hour.add(Hour(5,"10:30",false))
        hour.add(Hour(6,"11:00",false))
        hour.add(Hour(7,"11:30",false))
        hour.add(Hour(8,"12:00",true))
        hour.add(Hour(9,"12:30",true))
        hour.add(Hour(10,"13:00",true))
        hour.add(Hour(11,"13:30",true))
        hour.add(Hour(12,"14:00",true))
        hour.add(Hour(13,"14:30",true))
        hour.add(Hour(14,"15:00",true))
        hour.add(Hour(15,"15:30",true))
        hour.add(Hour(16,"16:00",true))
        hour.add(Hour(17,"16:30",true))

        room.clear()
        room.add(Room(0,"11th floor meeting Room 1",6 ,hour))
        room.add(Room(1,"11th floor meeting Room 2",9 ,hour))
        room.add(Room(2,"12th floor meeting Room 3",3 ,hour))
        room.add(Room(3,"12th floor meeting Room 4",12 ,hour))
        room.add(Room(4,"13th floor meeting Room 5",7 ,hour))
        room.add(Room(5,"13th floor meeting Room 6",20 ,hour))

        return room.map { it.toDomain() }.toMutableList()


    }

}
