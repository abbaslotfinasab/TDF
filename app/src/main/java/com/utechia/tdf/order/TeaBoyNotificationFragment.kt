package com.utechia.tdf.order

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
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentNotificationTeaboyBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TeaBoyNotificationFragment : DialogFragment() {

    private lateinit var binding: FragmentNotificationTeaboyBinding
    private val orderAdapter:NotificationAdapter = NotificationAdapter()
    private val teaOrderViewModel:TeaBoyOrderViewModel by viewModels()
    private var cartId = 0
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private lateinit var dateFormat: Date
    private var simple = ""


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
            cartId = requireArguments().getInt("cartId", 0)

        teaOrderViewModel.singleOrderTeaBoy(cartId)

        binding.notificationRecycler.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
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
        teaOrderViewModel.userOrderModel.observe(viewLifecycleOwner){


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
                    binding.date.visibility = View.VISIBLE
                    binding.order.visibility = View.VISIBLE
                    binding.username.text = it.data[0].user?.displayName
                    binding.location.text = it.data[0].user?.officeWorkStation
                    binding.order.text = it.data[0].id.toString()
                    dateFormat = sdf.parse(it.data[0].updatedAt!!)!!
                    simple = SimpleDateFormat("yyyy.MM.dd | HH:mm", Locale.getDefault()).format(dateFormat)
                    binding.date.text = "$simple"
                    orderAdapter.addData(it.data[0].cart?.items!!)
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