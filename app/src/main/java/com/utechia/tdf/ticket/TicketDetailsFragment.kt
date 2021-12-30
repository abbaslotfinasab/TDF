package com.utechia.tdf.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utechia.tdf.R
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.utechia.data.entity.Ticket
import com.utechia.tdf.databinding.FragmentTicketDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTicketDetailsBinding
    private lateinit var database: DatabaseReference
    private val chatAdapter :ChatAdapter = ChatAdapter()
    private var ticketId:String = "17377663707449043"

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

        val ticketListener = database.child("Ticket").child(ticketId)

        ticketListener.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val ticket = snapshot.getValue<Ticket>()
                chatAdapter.addData(ticket!!)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }

        })

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