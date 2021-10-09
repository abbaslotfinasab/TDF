package com.utechia.tdf.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendar
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateReservationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateReservationFragment : Fragment(),CalendarChangesObserver {

    private val calendar = Calendar.getInstance()
    private var currentMonth = 0


    private lateinit var binding: FragmentCreateReservationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        val notificationIcon: ImageView = requireActivity().findViewById(R.id.notification)
        val custom: ImageView = requireActivity().findViewById(R.id.customButton)
        val menu: ImageView = requireActivity().findViewById(R.id.menu)
        val back: ConstraintLayout = requireActivity().findViewById(R.id.back)
        val name: TextView = requireActivity().findViewById(R.id.name)
        val title: TextView = requireActivity().findViewById(R.id.title)
        val subTitle: TextView = requireActivity().findViewById(R.id.subTitle)
        navBar.visibility = View.GONE
        notificationIcon.isEnabled = false
        custom.visibility = View.GONE
        menu.visibility = View.INVISIBLE
        back.visibility = View.VISIBLE
        name.visibility = View.GONE
        title.visibility = View.VISIBLE
        subTitle.visibility = View.VISIBLE

        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]


        back.setOnClickListener {

            activity?.supportFragmentManager?.popBackStack()
        }


        val myCalendarViewManager = object : CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {

                val cal = Calendar.getInstance()
                cal.time = date

                return if (isSelected)
                    R.layout.item_date_selected
                else
                    R.layout.item_date
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                holder.itemView.findViewById<TextView>(R.id.tv_date_calendar_item).text = DateUtils.getDay3LettersName(date)
                holder.itemView.findViewById<TextView>(R.id.tv_day_calendar_item).text = DateUtils.getDayNumber(date)

            }
        }

        val myCalendarChangesObserver = object : CalendarChangesObserver {
            // you can override more methods, in this example we need only this one
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                super.whenSelectionChanged(isSelected, position, date)
            }
        }

        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // set date to calendar according to position
                val cal = Calendar.getInstance()
                cal.time = date
                //in this example sunday and saturday can't be selected, other item can be selected
                return when (cal[Calendar.DAY_OF_WEEK]) {
                   /* Calendar.SATURDAY -> false
                    Calendar.SUNDAY -> false*/
                    else -> true
                }
            }
        }
        val src:SingleRowCalendar = activity?.findViewById(R.id.main_single_row_calendar)!!
        val singleRowCalendar = src.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            futureDaysCount = 30
            includeCurrentDate = true
            init()
        }

         fun getDatesOfNextMonth(): List<Date> {
            currentMonth++ // + because we want next month
            if (currentMonth == 12) {
                // we will switch to january of next year, when we reach last month of year
                calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
                currentMonth = 0 // 0 == january
            }
            return getDates(mutableListOf())
        }

        fun getDatesOfPreviousMonth(): List<Date> {
            currentMonth-- // - because we want previous month
            if (currentMonth == -1) {
                // we will switch to december of previous year, when we reach first month of year
                calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] - 1)
                currentMonth = 11 // 11 == december
            }
            return getDates(mutableListOf())
        }

         fun getFutureDatesOfCurrentMonth(): List<Date> {
            // get all next dates of current month
            currentMonth = calendar[Calendar.MONTH]
            return getDates(mutableListOf())
        }

    }

    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }

}