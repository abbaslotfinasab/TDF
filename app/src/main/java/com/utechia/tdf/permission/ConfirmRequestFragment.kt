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
import androidx.navigation.fragment.findNavController
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentConfirmRequestBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.*
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ConfirmRequestFragment : DialogFragment() {

    private lateinit var binding: FragmentConfirmRequestBinding
    private val permissionViewModel: PermissionDetailsViewModel by viewModels()

    private val sdf:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    private var start = ""
    private var end = ""
    private var type = ""
    private var description = ""
    private var startTimeZone = ""
    private var endTimeZone = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmRequestBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {

            start = requireArguments().getString("start").toString()
            end = requireArguments().getString("end").toString()
            type = requireArguments().getString("type").toString()
            description = requireArguments().getString("description").toString()

        }

        startTimeZone = LocalDateTime.parse(start,sdf).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        endTimeZone = LocalDateTime.parse(end,sdf).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnAccept.setOnClickListener {
            permissionViewModel.postPermission(type,description,startTimeZone, endTimeZone)
            observer()
        }


    }

    private fun observer() {
        permissionViewModel.permissionModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.confirmRequestFragment_to_permissionFragment)


                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.confirmRequestFragment_to_permissionFragment)

                }
            }
        }
    }
}
