package com.utechia.tdf.ticket

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.NavHostFragment
import com.utechia.tdf.databinding.FragmentUploadDeleteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadReplyDeleteFragment : DialogFragment() {

    private lateinit var binding: FragmentUploadDeleteBinding
    private lateinit var navHostFragment : NavHostFragment
    private var positionId = 0




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadDeleteBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostFragment = requireActivity().supportFragmentManager.fragments[0] as NavHostFragment
        val parent = navHostFragment.childFragmentManager.primaryNavigationFragment as TicketDetailsFragment

        if (arguments != null)
            positionId = requireArguments().getInt("position", 0)

        binding.btnDelete.setOnClickListener {

            parent.deleteItem(positionId)

            dialog?.dismiss()

        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()

        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()

        }
    }

}