package com.utechia.tdf.order.user

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentOrderDetailsBinding
import com.utechia.tdf.order.teaboy.ItemDecorationOrderDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserOrderDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private val userOrderViewModel: UserOrderDetailsViewModel by viewModels()
    private val userOrderAdapter: UserOrderDetailsAdapter = UserOrderDetailsAdapter()
    private var cartId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            cartId = requireArguments().getInt(OrderEnum.ID.order)
        }

        userOrderViewModel.singleOrder(cartId)

        binding.recyclerView.apply {
            adapter = userOrderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(ItemDecorationOrderDetails())
        }


        binding.exit.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        observer()

    }

    private fun observer() {

        userOrderViewModel.userOrderModel.observe(viewLifecycleOwner) {


            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    userOrderAdapter.addData(it.data[0].cart?.items!!)
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}