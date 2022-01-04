package com.utechia.tdf.ticket

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utechia.tdf.R
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
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
    private lateinit var navHostFragment: NavHostFragment
    private val fragment: MessageFragment = MessageFragment()
    private val chatAdapter :ChatAdapter = ChatAdapter()
    private var ticketId:String = ""
    private var status:String = ""
    private var mId:Int = 0

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

        navHostFragment = requireActivity().supportFragmentManager.fragments[0] as NavHostFragment





        binding.btnReply.bringToFront()
        binding.btnClose.bringToFront()

        if (arguments != null) {
            ticketId = requireArguments().getString("fid","")
            mId = requireArguments().getInt("ticketId",0)
            status = requireArguments().getString("status","")

        }

        val ticketListener = database.child("Ticketmessage").child("Ticket-$ticketId")

        ticketListener.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatAdapter.chat.clear()
                binding.prg.visibility = View.GONE
                binding.btnReply.visibility = View.VISIBLE
                binding.btnClose.visibility = View.VISIBLE

                snapshot.children.forEach {
                    chatAdapter.addData(it.getValue<Chat>()!!)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }
        })

        if (status == "Close"){
            binding.btnClose.isEnabled = false
            binding.btnReply.isEnabled = false
            binding.btnClose.isClickable = false
            binding.btnReply.isClickable = false
            binding.btnClose.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.gray))
            binding.btnReply.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.gray))

        }else{
            binding.btnClose.isEnabled = true
            binding.btnReply.isEnabled = true
            binding.btnClose.isClickable = true
            binding.btnReply.isClickable = true
            binding.btnClose.setBackgroundColor(Color.parseColor("#FF6464"))
            binding.btnReply.setBackgroundColor(Color.parseColor("#3360DD"))

        }

        binding.recyclerView.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ChatItemDecoration())
        }

        binding.btnReply.setOnClickListener {
            showDialog()
        }

        binding.btnClose.setOnClickListener {
            val bundle = bundleOf("ticketId" to mId)
            findNavController().navigate(R.id.action_ticketDetailsFragment_to_closeTicketFragment,bundle)

        }
    }

    private fun showDialog() {
        val bundle = bundleOf("ticketId" to ticketId)
        fragment.arguments = bundle
        fragment.show(parentFragmentManager,"Tag")
    }

    fun openGallery(){

        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(2048)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080) //Final image resolution will be less than 1080 x 1080(Optional)
            .galleryOnly()
            .start()

    }

    fun openCamera(){

        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(2048)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080) //Final image resolution will be less than 1080 x 1080(Optional)
            .cameraOnly()
            .start()

    }

    fun openFile(){
        val intent: Intent
        val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.type = "application/pdf"
        intent = Intent.createChooser(chooseFile, "Choose a file")
        startActivityForResult(intent, Activity.RESULT_OK)

    }

    private fun replacement(){
       fragment.replacement0()
    }

    fun deleteItem(position:Int){
        fragment.uploadOrder.file.removeAt(position)
        fragment.uploadOrder.notifyItemRemoved(position)

        if(fragment.uploadOrder.file.isEmpty()){
            replacement()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!
                // Use Uri object instead of File to avoid storage permissions
                fragment.uploadOrder.addData(uri)
                fragment.replacement1()

            }

            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
        }
    }
}