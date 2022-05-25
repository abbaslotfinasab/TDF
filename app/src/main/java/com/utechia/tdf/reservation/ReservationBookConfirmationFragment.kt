package com.utechia.tdf.reservation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentReservationBookConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationBookConfirmationFragment : DialogFragment(),View.OnClickListener {

    private lateinit var binding: FragmentReservationBookConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationBookConfirmationBinding.inflate(inflater, container, false)
        binding.exit.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.btnAccept.setOnClickListener(this)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.exit ->{
                dialog?.dismiss()
            }

            R.id.btnCancel ->{
                dialog?.dismiss()
            }

            R.id.btnAccept ->{
                BookListener.bookListener.postValue(true)
                dialog?.dismiss()
            }
        }
    }
}