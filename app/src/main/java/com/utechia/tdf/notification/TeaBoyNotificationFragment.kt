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
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentNotificationTeaboyBinding
import com.utechia.tdf.order.teaboy.TeaBoyOrderDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class TeaBoyNotificationFragment : DialogFragment() {

    private lateinit var binding: FragmentNotificationTeaboyBinding
    private val orderAdapterTeaBoy: TeaBoyNotificationAdapter = TeaBoyNotificationAdapter()
    private val teaOrderViewModel: TeaBoyOrderDetailsViewModel by viewModels()
    private var cartId = 0
    private var timeZone = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationTeaboyBinding.inflate(inflater, container, false)

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
            cartId = requireArguments().getInt(OrderEnum.ID.order, 0)

        teaOrderViewModel.singleOrderTeaBoy(cartId)

        binding.notificationRecycler.apply {
            adapter = orderAdapterTeaBoy
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(ItemTeaBoyNotificationDecoration())
        }

        binding.exit.setOnClickListener {
            dialog?.dismiss()

        }

        binding.btnAccept.setOnClickListener {
            findNavController().navigate(R.id.action_teaBoyNotificationFragment_to_teaBoyOrdersFragment)
        }

        observer()

    }

    private fun observer() {
        teaOrderViewModel.orderModel.observe(viewLifecycleOwner){


            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.btnAccept.visibility = View.VISIBLE
                    binding.exit.visibility = View.VISIBLE
                    binding.imageView4.visibility = View.VISIBLE
                    binding.textview1.visibility = View.VISIBLE
                    binding.notificationRecycler.visibility = View.VISIBLE
                    binding.imageView13.visibility = View.VISIBLE
                    binding.imageView12.visibility = View.VISIBLE
                    binding.username.visibility = View.VISIBLE
                    binding.subtitle.visibility = View.VISIBLE
                    binding.location.visibility = View.VISIBLE
                    binding.date.visibility = View.VISIBLE
                    binding.order.visibility = View.VISIBLE
                    binding.username.text = it.data[0].user?.displayName
                    binding.location.text = it.data[0].user?.officeFloor
                    binding.order.text = it.data[0].id.toString()
                    timeZone = OffsetDateTime.parse(it.data[0].updatedAt).atZoneSameInstant(
                        ZoneId.systemDefault()
                    ).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"))
                    binding.date .text = timeZone
                    orderAdapterTeaBoy.addData(it.data[0].cart?.items!!)
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.btnAccept.visibility = View.INVISIBLE
                    binding.exit.visibility = View.INVISIBLE
                    binding.imageView4.visibility = View.INVISIBLE
                    binding.textview1.visibility = View.INVISIBLE
                    binding.notificationRecycler.visibility = View.INVISIBLE
                    binding.imageView13.visibility = View.INVISIBLE
                    binding.imageView12.visibility = View.INVISIBLE
                    binding.username.visibility = View.INVISIBLE
                    binding.subtitle.visibility = View.INVISIBLE
                    binding.date.visibility = View.INVISIBLE
                    binding.order.visibility = View.INVISIBLE


                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
            }
        }
    }

}