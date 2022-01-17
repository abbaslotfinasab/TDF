package com.utechia.tdf.permission

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentRequestDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class RequestDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentRequestDetailsBinding
    private val permissionViewModel:PermissionViewModel by viewModels()
    private var permissionId = 0
    private var startTimeZone = ""
    private var endTimeZone = ""


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
            permissionId = requireArguments().getInt("permissionId")

        }

        permissionViewModel.getSinglePermission(permissionId)

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        observer()

    }

    private fun observer() {
        permissionViewModel.permissionModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.from.visibility = View.VISIBLE
                    binding.to.visibility = View.VISIBLE
                    binding.prg.visibility = View.GONE
                    binding.title.text = it.data[0].type
                    binding.description.text = it.data[0].description

                    startTimeZone = OffsetDateTime.parse(it.data[0].datestarts).atZoneSameInstant(
                        ZoneId.systemDefault()
                    ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
                    binding.startDate.text = "$startTimeZone"

                    endTimeZone = OffsetDateTime.parse(it.data[0].dateends).atZoneSameInstant(
                        ZoneId.systemDefault()
                    ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
                    binding.endDate.text = "$endTimeZone"

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.from.visibility = View.GONE
                    binding.to.visibility = View.GONE


                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.from.visibility = View.GONE
                    binding.to.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}