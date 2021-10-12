package com.utechia.data.utile

import com.utechia.data.dao.RoomDao
import com.utechia.data.entity.Room
import javax.inject.Inject

class FakeData @Inject constructor(
    private val roomDao:RoomDao
) {


    suspend fun sendData(){

        val room0 = Room(1,"Meeting Room 1",3,
            "https://s3.amazonaws.com/static-webstudio-accorhotels-usa-1.wp-ha.fastbooking.com/wp-content/uploads/sites/17/2020/06/15083055/SPPH_meeting_thumb_02.jpg",13,10)

        roomDao.saveRoom(room0)

        val room1 = Room(2,"Meeting Room 2",6,
            "https://mk0peerspaceres622pi.kinstacdn.com/wp-content/uploads/Eco-Friendly-Executive-Boardroom-santa-monica-la-los-angeles-rental-1200x600.webp",13,10)

        roomDao.saveRoom(room1)

        val room2 = Room(3,"Meeting Room 3",9,
            "https://s3.amazonaws.com/static-webstudio-accorhotels-usa-1.wp-ha.fastbooking.com/wp-content/uploads/sites/17/2020/06/15083055/SPPH_meeting_thumb_02.jpg",13,10)

        roomDao.saveRoom(room2)



    }


}