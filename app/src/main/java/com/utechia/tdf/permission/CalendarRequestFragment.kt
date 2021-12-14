package com.utechia.tdf.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCalendarRequestBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CalendarRequestFragment : DialogFragment() {

    private lateinit var binding: FragmentCalendarRequestBinding
    private var calendar:Calendar = Calendar.getInstance()
    private var startTime = ""
    private var endTime = ""
    private var first = ""
    private var last = ""
    private var hh = ""
    private var mm = ""
    private var fMonth = ""
    private var lMonth = ""
    private var fdd = ""
    private var ldd = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar.set(calendar.get(Calendar.YEAR)+3,5,29,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND))

        binding.btnCancel.setOnClickListener {

            if (startTime !="" && endTime !="" && first!="" && last !="") {
                val bundle = bundleOf("start" to startTime, "end" to endTime)
                findNavController().navigate(
                    R.id.calendarRequestFragment_to_createRequestFragment,
                    bundle
                )
                startTime = ""
                endTime = ""
                first = ""
                last = ""
                hh = ""
                mm = ""
                fMonth = ""
                lMonth = ""
                fdd = ""
                ldd = ""
                calendar.clear()
            }else {
                Toast.makeText(context,"Please select your request date",Toast.LENGTH_SHORT).show()
            }
        }
        binding.calendar.setSelectableDateRange(Calendar.getInstance(),calendar)

        binding.calendar.setCalendarListener(object :CalendarListener{
            override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                selectTime(2)
                fMonth = "${startDate.get(Calendar.MONTH)+1}"
                fMonth = if(fMonth.length<2)
                    "0${startDate.get(Calendar.MONTH)+1}"
                else
                    "${startDate.get(Calendar.MONTH)+1}"

                lMonth = "${endDate.get(Calendar.MONTH)+1}"
                lMonth = if(lMonth.length<2)
                    "0${startDate.get(Calendar.MONTH)+1}"
                else
                    "${startDate.get(Calendar.MONTH)+1}"

                fdd = "${startDate.get(Calendar.DAY_OF_MONTH)}"
                fdd= if (fdd.length<2)
                    "0${startDate.get(Calendar.DAY_OF_MONTH)}"
                else
                    "${startDate.get(Calendar.DAY_OF_MONTH)}"

                ldd = "${endDate.get(Calendar.DAY_OF_MONTH)}"
                ldd = if (ldd.length<2)
                    "0${endDate.get(Calendar.DAY_OF_MONTH)}"
                else
                    "${endDate.get(Calendar.DAY_OF_MONTH)}"

                startTime = "${startDate.get(Calendar.YEAR)}-$fMonth-$fdd"
                endTime= "${endDate.get(Calendar.YEAR)}-$lMonth-$ldd"
            }
            override fun onFirstDateSelected(startDate: Calendar) {
                binding.start.text = ""
                binding.end.text = ""

                selectTime(1)
                fMonth = "${startDate.get(Calendar.MONTH)+1}"
                fMonth = if(fMonth.length<2)
                    "0${startDate.get(Calendar.MONTH)+1}"
                else
                    "${startDate.get(Calendar.MONTH)+1}"

                fdd = "${startDate.get(Calendar.DAY_OF_MONTH)}"
                fdd = if (fdd.length<2)
                    "0${startDate.get(Calendar.DAY_OF_MONTH)}"
                else
                    "${startDate.get(Calendar.DAY_OF_MONTH)}"
                startTime = "${startDate.get(Calendar.YEAR)}-$fMonth-$fdd"
                binding.end.text = ""
            }

        })
    }

    private fun selectTime(kind:Int){
        val dialog = SnapTimePickerDialog.Builder().apply {
            setTitle(R.string.timetitle)
            setThemeColor(R.color.status)
            setTitleColor(R.color.white)
            setPositiveButtonText(R.string.accept)
            setNegativeButtonText(R.string.reject)
            setPositiveButtonColor(R.color.status)
            setNegativeButtonColor(R.color.status)
        }.build().apply {
            setListener{hour, minute ->
                when(kind){
                    1->{
                        hh = hour.toString()
                        mm = minute.toString()

                        first = if (hh.length<2 && mm.length<2)
                            "0$hour:0$minute"
                        else if (hh.length<2)
                            "0$hour:$minute"
                        else if (mm.length<2)
                            "$hour:0$minute"
                        else
                            "$hour:$minute"

                        binding.start.text = "From: $startTime-$first"
                        startTime = "${startTime}T$first:00.000Z"
                        binding.end.text = ""
                    }
                    2->{
                        hh = hour.toString()
                        mm = minute.toString()

                        last = if (hh.length<2 && mm.length<2)
                            "0$hour:0$minute"
                        else if (hh.length<2)
                            "0$hour:$minute"
                        else if (mm.length<2)
                            "$hour:0$minute"
                        else
                            "$hour:$minute"

                        startTime = "${startTime}T$first:00.000Z"
                        binding.end.text = "To: $endTime-$last"
                        endTime = "${endTime}T$last:00.000Z"

                    }
                }
            }
        }
        dialog.show(parentFragmentManager, tag)
    }

}