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
    private var startTime = ""
    private var endTime = ""
    private var first = ""
    private var last = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            }else {
                Toast.makeText(context,"Please select your request date",Toast.LENGTH_SHORT).show()
            }
        }

        binding.calendar.setCalendarListener(object :CalendarListener{
            override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                selectTime(2)
                startTime = "${startDate.get(Calendar.MONTH)+1}.${startDate.get(Calendar.DAY_OF_MONTH)}.${startDate.get(Calendar.YEAR)}"
                endTime= "${endDate.get(Calendar.MONTH)+1}.${endDate.get(Calendar.DAY_OF_MONTH)}.${endDate.get(Calendar.YEAR)}"
            }
            override fun onFirstDateSelected(startDate: Calendar) {
                selectTime(1)
                startTime = "${startDate.get(Calendar.MONTH)+1}.${startDate.get(Calendar.DAY_OF_MONTH)}.${startDate.get(Calendar.YEAR)}"
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
                        first = "$hour:$minute"
                        startTime = "From: $startTime - $first"
                        binding.start.text = startTime
                        binding.end.text = ""
                    }
                    2->{
                        last = "$hour:$minute"
                        endTime = "To: $endTime - $last"
                        binding.end.text = endTime

                    }
                }

            }
        }
        dialog.show(parentFragmentManager, tag)
    }

}