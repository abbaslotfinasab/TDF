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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.utechia.data.entity.Chat
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentTicketDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTicketDetailsBinding
    private lateinit var database: DatabaseReference
    private lateinit var navHostFragment: NavHostFragment
    private val fragment: MessageFragment = MessageFragment()
    private val uploadViewModel:UploadViewModel by viewModels()
    private val ticketViewModel:TicketDetailsViewModel by viewModels()
    private val chatAdapter :ChatAdapter = ChatAdapter(this)
    private var mId = 0




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
            mId = requireArguments().getInt("ticketId",0)
        }

        ticketViewModel.getSingleTicket(mId)
        detailsObserver()

        binding.btnReply.setOnClickListener {
            showDialog()
        }

        binding.btnClose.setOnClickListener {
            val bundle = bundleOf("ticketId" to mId)
            findNavController().clearBackStack(R.id.ticketDetailsFragment)
            findNavController().navigate(R.id.action_ticketDetailsFragment_to_closeTicketFragment,bundle)

        }
    }

    private fun showDialog() {
        fragment.uploadOrder.localFile.clear()
        fragment.uploadOrder.globalFile.clear()
        val bundle = bundleOf("ticketId" to mId)
        fragment.arguments = bundle
        if (!fragment.isAdded)
        fragment.show(childFragmentManager,"Tag")
        else
            fragment.dialog?.show()

    }

    fun openGallery(){

        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(728, 1024) //Final image resolution will be less than 1080 x 1080(Optional)
            .galleryOnly()
            .start()

    }

    fun openCamera(){

        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(728, 1024) //Final image resolution will be less than 1080 x 1080(Optional)
            .cameraOnly()
            .start()

    }

    fun openFile(){
        val intent: Intent
        val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.type = "image/*"
        intent = Intent.createChooser(chooseFile, "Choose a file")
        startActivityForResult(intent, Activity.RESULT_OK)

    }

    private fun replacement(){
       fragment.replacement0()
    }

    fun rating(mId:Int){
        val bundle = bundleOf("ticketId" to mId)
        findNavController().clearBackStack(R.id.ticketDetailsFragment)
        findNavController()
            .navigate(R.id.action_ticketDetailsFragment_to_ticketRatingFragment,bundle)
    }

    fun deleteItem(position:Int){
        fragment.uploadOrder.localFile.removeAt(position)
        fragment.uploadOrder.notifyItemRemoved(position)

        if(fragment.uploadOrder.localFile.isEmpty()){
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
                uploadViewModel.uploadFile(uri)
                fragment.uploadOrder.addData(uri.toString())
                fragment.replacement1()
                uploadObserver()
            }

            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadObserver() {
        uploadViewModel.uploadModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    fragment.hidePrg()
                    fragment.uploadOrder.globalFile.add(it.data.path!!)
                }

                is Result.Loading -> {
                    fragment.showPrg()
                }

                is Result.Error -> {
                    fragment.hidePrg()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun detailsObserver() {
        ticketViewModel.ticketModel.observe(viewLifecycleOwner){ it0 ->

            when (it0) {
                is Result.Success -> {

                    binding.prg.visibility = View.GONE
                    chatAdapter.addDetails(it0.data)


                    val ticketListener = database.child("Ticketmessage").child("Ticket-${it0.data.fid!!}").orderByValue()

                    ticketListener.addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            chatAdapter.chat.clear()
                            binding.prg.visibility = View.GONE
                            binding.btnReply.visibility = View.VISIBLE
                            binding.btnClose.visibility = View.VISIBLE
                            snapshot.children.forEach {
                                chatAdapter.addData(it.getValue<Chat>()!!)
                            }
                            binding.recyclerView.scrollToPosition(chatAdapter.chat.size)

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
                        }
                    })

                    binding.recyclerView.apply {
                        adapter = chatAdapter
                        layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                        addItemDecoration(ChatItemDecoration())
                    }

                    if (it0.data.status == "Close"){
                        binding.btnReply.visibility = View.VISIBLE
                        binding.btnClose.visibility = View.VISIBLE
                        binding.btnClose.isEnabled = false
                        binding.btnReply.isEnabled = false
                        binding.btnClose.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.gray))
                        binding.btnReply.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.gray))

                    }else{
                        binding.btnReply.visibility = View.VISIBLE
                        binding.btnClose.visibility = View.VISIBLE
                        binding.btnClose.isEnabled = true
                        binding.btnReply.isEnabled = true
                        binding.btnClose.setBackgroundColor(Color.parseColor("#FF6464"))
                        binding.btnReply.setBackgroundColor(Color.parseColor("#3360DD"))

                    }
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it0.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}