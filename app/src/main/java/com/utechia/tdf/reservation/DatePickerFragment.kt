package com.utechia.tdf.reservation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.domain.enum.ReservationEnum
import com.utechia.domain.model.reservation.DateModel
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentDatePickerBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DatePickerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDatePickerBinding
    private var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    private var dayOfWeek = LocalDateTime.now().dayOfWeek.name
    private var _year = LocalDateTime.now().year
    private var month = LocalDateTime.now().month.value
    private var day = LocalDateTime.now().dayOfMonth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDatePickerBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setOnShowListener{dialog ->
                val d = dialog as BottomSheetDialog
                val bottomSheetInternal: FrameLayout? = d.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                BottomSheetBehavior.from(bottomSheetInternal!!).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return binding.root
    }

    override fun getTheme(): Int = R.style.RoomBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            _year = year
            month = monthOfYear
            day = dayOfMonth
        }

        binding.btnSelect.setOnClickListener {
            date = LocalDateTime.of(_year,month+1,day,0,0).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()
            dayOfWeek = LocalDateTime.of(_year,month+1,day,0,0).dayOfWeek.name
            DateListener.datePickerListener.postValue(DateModel(0,day.toString(),dayOfWeek,date))
            dialog?.dismiss()
        }
    }
}

