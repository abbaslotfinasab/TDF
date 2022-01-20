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
import com.utechia.tdf.databinding.FragmentNotificationDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentNotificationDetailsBinding
    private val notificationViewModel:NotificationViewModel by viewModels()
    private var nId = 0
    private var title = ""
    private var body = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationDetailsBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            nId = requireArguments().getInt("nId", 0)
            title = requireArguments().getString("title", "")
            body = requireArguments().getString("body", "")
        }

        binding.title.text = title
        binding.subtitle.text = body

        notificationViewModel.readNotification(nId)

        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnAccept.setOnClickListener {
            findNavController().navigate(R.id.action_notificationDetailsFragment_to_notificationFragment)
            dialog?.dismiss()
        }
    }
}