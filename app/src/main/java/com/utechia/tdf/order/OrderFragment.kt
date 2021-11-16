package com.utechia.tdf.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentOrderBinding
import com.utechia.tdf.refreshment.RefreshmentViewModel
import com.utechia.tdf.reservation.ItemDecorationBooked
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

   /* private lateinit var binding: FragmentOrderBinding
    private val refreshmentViewModel: RefreshmentViewModel by viewModels()
    private val orderAdapter: OrderAdapter = OrderAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshmentViewModel.getRefreshment(4)


        binding.recyclerView.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationBooked())
        }

        binding.appCompatButton.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_orderResultFragment)
        }


        observer()



    }

    private fun observer() {

        refreshmentViewModel.teaBoyModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.appCompatButton.visibility = View.VISIBLE
                    binding.textView6.visibility = View.VISIBLE
                    binding.textView7.visibility = View.VISIBLE
                    binding.time.visibility = View.VISIBLE

                    orderAdapter.addData(it.data)
                }


                is Result.Loading -> {
                    binding.appCompatButton.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView7.visibility = View.GONE
                    binding.time.visibility = View.GONE
                    binding.prg.visibility = View.VISIBLE
                }


                is Result.Error -> {
                    orderAdapter.orders.clear()
                    binding.appCompatButton.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    binding.textView7.visibility = View.GONE
                    binding.time.visibility = View.GONE
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
*/
}