package com.utechia.tdf.ticket

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentUploadBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class UploadFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUploadBinding
    private lateinit var navHostFragment :NavHostFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostFragment = requireActivity().supportFragmentManager.fragments[0] as NavHostFragment
        val parent = navHostFragment.childFragmentManager.primaryNavigationFragment as CreateTicketFragment

        binding.camera.setOnClickListener {
            parent.openCamera()
            dialog?.dismiss()
        }

        binding.gallery.setOnClickListener {
            parent.openGallery()
            dialog?.dismiss()
        }

       /* binding.file.setOnClickListener {
            parent.openFile()
            dialog?.dismiss()
        }
*/
    }
}