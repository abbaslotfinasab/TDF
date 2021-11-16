package com.utechia.tdf.refreshment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.utechia.tdf.databinding.FragmentCancelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancelFragment : DialogFragment() {

    private lateinit var binding: FragmentCancelBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCancelBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}