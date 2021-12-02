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

@AndroidEntryPoint
class RequestDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentRequestDetailsBinding
    private val permissionViewModel:PermissionViewModel by viewModels()
    private var permissionId = 0

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
                    binding.prg.visibility = View.GONE
                    binding.title.text = it.data[0].type
                    binding.description.text = it.data[0].description
                    binding.date.text = "From:${it.data[0].datestarts}\nTo:${it.data[0].dateends}"

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}