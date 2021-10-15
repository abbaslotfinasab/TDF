package com.utechia.data.utile

import com.utechia.data.dao.RoomDao
import com.utechia.data.entity.Room
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FakeData @Inject constructor(
    private val roomDao:RoomDao
) {

    private var sdf = SimpleDateFormat("MM")
    private val mDate: Date = Date()
    private var currentMonth = sdf.format(mDate).toInt()

    private var _sdf = SimpleDateFormat("dd")
    private val dDate: Date = Date()
    private var currentDay = _sdf.format(dDate).toInt()


    suspend fun sendData(){

        val room0 = Room(0,"Meeting Room 1",3,
            "https://s3.amazonaws.com/static-webstudio-accorhotels-usa-1.wp-ha.fastbooking.com/wp-content/uploads/sites/17/2020/06/15083055/SPPH_meeting_thumb_02.jpg",currentDay,currentMonth)

        roomDao.saveRoom(room0)

        val room1 = Room(1,"Meeting Room 2",6,
            "https://mk0peerspaceres622pi.kinstacdn.com/wp-content/uploads/Eco-Friendly-Executive-Boardroom-santa-monica-la-los-angeles-rental-1200x600.webp",currentDay,currentMonth)

        roomDao.saveRoom(room1)

        val room2 = Room(2,"Meeting Room 3",9,
            "https://s3.amazonaws.com/static-webstudio-accorhotels-usa-1.wp-ha.fastbooking.com/wp-content/uploads/sites/17/2020/06/15083055/SPPH_meeting_thumb_02.jpg",currentDay,currentMonth)

        roomDao.saveRoom(room2)



    }


}