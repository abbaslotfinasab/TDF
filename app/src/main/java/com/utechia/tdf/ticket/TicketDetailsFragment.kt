package com.utechia.tdf.ticket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utechia.tdf.R
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.utechia.data.entity.Chat
import com.utechia.tdf.databinding.FragmentTicketDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTicketDetailsBinding
    private lateinit var database: DatabaseReference
    private val chatAdapter :ChatAdapter = ChatAdapter()
    private var ticketId:String = ""
    private var k = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database("https://tdf-oms-default-rtdb.asia-southeast1.firebasedatabase.app/").reference


        binding.btnReply.bringToFront()
        binding.btnClose.bringToFront()

        if (arguments != null) {
            ticketId = requireArguments().getString("fid","")
        }

        val ticketListener = database.child("Ticketmessage").child("Ticket-$ticketId")

        ticketListener.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    chatAdapter.addData(it.getValue<Chat>()!!)
                    binding.prg.visibility = View.GONE
                }
            }


            override fun onCancelled(error: DatabaseError) {

                Log.e("error", error.message)


            }
        })

        binding.recyclerView.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }

        binding.btnReply.setOnClickListener {
            val bundle = bundleOf("ticketId" to ticketId)
            findNavController().navigate(R.id.action_ticketDetailsFragment_to_messageFragment,bundle)
        }

        binding.btnClose.setOnClickListener {
            val bundle = bundleOf("ticketId" to ticketId)
            findNavController().navigate(R.id.action_ticketDetailsFragment_to_closeTicketFragment,bundle)

        }
    }
}