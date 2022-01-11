package com.utechia.tdf.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentTicketSystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketSystemFragment : Fragment() {

    private lateinit var binding: FragmentTicketSystemBinding
    private val ticketViewModel:TicketViewModel by viewModels()
    private val ticketAdapter:TicketAdapter = TicketAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ticketViewModel.getAllTicket("Open")

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.position){

                    0 -> {
                        ticketViewModel.getAllTicket("Open")
                        observer()

                    }
                    else -> {
                        ticketViewModel.getAllTicket("Close")
                        observer()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

               /* when(tab?.position){

                    0 -> {
                        ticketViewModel.getAllTicket("Open")
                        observer()

                    }
                    else -> {
                        ticketViewModel.getAllTicket("Close")
                        observer()
                    }
                }*/

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

                /* when(tab?.position){

                   0 -> {
                       ticketViewModel.getAllTicket("Open")
                       observer()

                   }
                   else -> {
                       ticketViewModel.getAllTicket("Close")
                       observer()
                   }
               }*/

            }

        })


        binding.plus.setOnClickListener {

            findNavController().navigate(R.id.action_ticketSystemFragment_to_createTicketFragment)

        }

        binding.more.setOnClickListener {
            findNavController().navigate(R.id.action_ticketSystemFragment_to_createTicketFragment)

        }

        binding.recyclerView.apply {
            adapter = ticketAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(TicketItemDecortation())
        }

        observer()

    }

    private fun observer() {
        ticketViewModel.ticketModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE

                    if (it.data.size!=0){

                        binding.recyclerView.visibility = View.VISIBLE
                        binding.title.visibility = View.GONE
                        binding.plus.visibility = View.GONE
                        binding.more.visibility = View.VISIBLE
                        binding.imageView4.visibility = View.GONE
                        ticketAdapter.addData(it.data)

                    }
                    else{
                        binding.recyclerView.visibility = View.GONE
                        binding.title.visibility = View.VISIBLE
                        binding.plus.visibility = View.VISIBLE
                        binding.more.visibility = View.GONE
                        binding.imageView4.visibility = View.VISIBLE
                    }

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.title.visibility = View.GONE
                    binding.plus.visibility = View.GONE
                    binding.more.visibility = View.GONE
                    binding.imageView4.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.title.visibility = View.GONE
                    binding.plus.visibility = View.GONE
                    binding.more.visibility = View.GONE
                    binding.imageView4.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}