package com.utechia.tdf.refreshment


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
import com.utechia.tdf.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    val cartViewModel:CartViewModel by viewModels()
    private val cartAdapter:CartAdapter = CartAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel.getCart()

        binding.appBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.recyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }


        observer()

    }

    private fun observer() {

        cartViewModel.cartModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE

                    if (it.data.size==0){
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                    }
                    else{
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyLayout.visibility = View.GONE
                        cartAdapter.addData(it.data[0].items)
                    }

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}