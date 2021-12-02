package com.utechia.tdf.permission

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCancelBinding
import com.utechia.tdf.databinding.FragmentCancelRequestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancelRequestFragment : DialogFragment() {

    private lateinit var binding: FragmentCancelRequestBinding
    private val permissionViewModel:PermissionViewModel by viewModels()
    private var permissionId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCancelRequestBinding.inflate(inflater, container, false)

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

        binding.btnKeep.setOnClickListener {
            dialog?.dismiss()
        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            permissionViewModel.updatePermission(permissionId,"cancelled")
            findNavController().clearBackStack(R.id.permissionFragment)
            findNavController().navigate(R.id.cancelRequestFragment_to_permissionFragment)
            dialog?.dismiss()
        }



    }

}