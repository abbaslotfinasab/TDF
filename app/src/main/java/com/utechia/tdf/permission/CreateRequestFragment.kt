package com.utechia.tdf.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateRequestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRequestFragment : DialogFragment() {

    private lateinit var binding: FragmentCreateRequestBinding
    private val permissionTypeViewModel: PermissionTypeViewModel by viewModels()
    private lateinit var radioButton:RadioButton
    private var start = ""
    private var end = ""
    private var type = ""
    private var description = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionTypeViewModel.getPermission()

        if (arguments !=null){

            start = requireArguments().getString("start").toString()
            end = requireArguments().getString("end").toString()


        }
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
           group.findViewById<RadioButton>(checkedId)?.let {
               type = it.text.toString()
           }
        }

        binding.btnRequest.setOnClickListener {
            if (type !="") {
                description = binding.description.text.toString()
                val bundle = bundleOf(
                    "start" to start,
                    "end" to end,
                    "type" to type,
                    "description" to description
                )
                findNavController().navigate(
                    R.id.createRequestFragment_to_confirmRequestFragment,
                    bundle
                )
            }
            else
                Toast.makeText(context,"Please select type of permission",Toast.LENGTH_SHORT).show()

        }

        observer()

    }

    private fun observer() {
        permissionTypeViewModel.permissionModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    for (i in 0 until it.data.size){

                        radioButton = RadioButton(context,null,0,R.style.Widget_AppCompat_CompoundButton_CheckBox)
                        radioButton.text = it.data[i]
                        radioButton.id = i
                        binding.radioGroup.addView(radioButton)

                    }

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