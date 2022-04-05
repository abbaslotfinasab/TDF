package com.utechia.tdf.notification

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
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentReadAllNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadAllNotificationFragment : DialogFragment() {

    private lateinit var binding: FragmentReadAllNotificationsBinding
    private val ticketViewModel:NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadAllNotificationsBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnYes.setOnClickListener {
           ticketViewModel.readNotification(0,true)
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()

        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        observer()
    }


    private fun observer() {
        ticketViewModel.notificationModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_readAllNotificationFragment_to_notificationFragment)
                    dialog?.dismiss()

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                }
            }
        }
    }
}