package com.utechia.tdf.permission

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.utechia.tdf.databinding.FragmentRequestDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class RequestDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentRequestDetailsBinding
    private var startTimeZone = ""
    private var endTimeZone = ""
    private var title = ""
    private var description = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestDetailsBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments !=null){
            title = requireArguments().getString("title","")
            description = requireArguments().getString("description","")
            startTimeZone = requireArguments().getString("startTime","")
            endTimeZone = requireArguments().getString("endTime","")
        }

        binding.title.text = title

        binding.description.text = description

        startTimeZone = OffsetDateTime.parse(startTimeZone).atZoneSameInstant(
            ZoneId.systemDefault()
        ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
        binding.startDate.text = startTimeZone

        endTimeZone = OffsetDateTime.parse(endTimeZone).atZoneSameInstant(
            ZoneId.systemDefault()
        ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
        binding.endDate.text = startTimeZone


        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }
}