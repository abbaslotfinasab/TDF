package com.utechia.tdf.notification

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
import com.utechia.tdf.databinding.FragmentNotificationDeleteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationDeleteFragment : DialogFragment() {

    private lateinit var binding: FragmentNotificationDeleteBinding
    private val notificationViewModel:NotificationDetailsViewModel by viewModels()
    private var nId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationDeleteBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null)
            nId = requireArguments().getInt("nId", 0)

        binding.btnDelete.setOnClickListener {

            notificationViewModel.deleteNotification(nId)
            observer()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().clearBackStack(R.id.notificationDeleteFragment)
            findNavController().navigate(R.id.action_notificationDeleteFragment_to_notificationFragment)
            dialog?.dismiss()

        }

        binding.exit.setOnClickListener {
            findNavController().clearBackStack(R.id.notificationDeleteFragment)
            findNavController().navigate(R.id.action_notificationDeleteFragment_to_notificationFragment)
            dialog?.dismiss()

        }

    }

    private fun observer() {

        notificationViewModel.notificationModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    findNavController().clearBackStack(R.id.notificationDeleteFragment)
                    findNavController().navigate(R.id.action_notificationDeleteFragment_to_notificationFragment)
                    dialog?.dismiss()

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.btnDelete.isEnabled = false
                    binding.btnCancel.isEnabled = false
                    binding.exit.isEnabled = false

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().clearBackStack(R.id.notificationDeleteFragment)
                    findNavController().navigate(R.id.action_notificationDeleteFragment_to_notificationFragment)
                    dialog?.dismiss()
                }
            }
        }
    }
}