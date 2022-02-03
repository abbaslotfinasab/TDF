package com.utechia.tdf.ticket

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.domain.enum.TicketEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentMessageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMessageBinding
    private val ticketViwModel:TicketViewModel by viewModels()
    val uploadOrder:UploadReplyAdapter = UploadReplyAdapter(this)
    private var ticketId = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setOnShowListener{dialog ->
                val d = dialog as BottomSheetDialog
                val bottomSheetInternal:FrameLayout? = d.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                BottomSheetBehavior.from(bottomSheetInternal!!).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return binding.root
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onResume() {
        super.onResume()
        if (uploadOrder.localFile.size !=0){
            binding.uploadLayout0.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
        else{
            binding.uploadLayout0.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments != null)
            ticketId = requireArguments().getInt(TicketEnum.Id.ticket, 0)

        binding.recyclerView.apply {
            adapter = uploadOrder
            layoutManager = GridLayoutManager(context,calculateNoOfColumns(context, 100.0F))
        }

        binding.appCompatButton.setOnClickListener {
            ticketViwModel.replyTicket(ticketId, uploadOrder.globalFile,binding.description.text.toString())
        }

        binding.uploadLayout0.setOnClickListener {
            showUpload()
        }
        observer()
    }

    private fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int { // For example columnWidthdp=180
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt() // +0.5 for correct rounding to int.
    }

     fun replacement0(){
        binding.uploadLayout0.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

     fun replacement1(){
        binding.uploadLayout0.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    fun showDelete(bundle: Bundle){
        findNavController().navigate(R.id.action_messageFragment_to_uploadReplyDeleteFragment,bundle)
    }
    fun showUpload() {
        findNavController().navigate(R.id.action_messageFragment_to_uploadFragment)

    }
    fun showImage(bundle: Bundle){
        findNavController().navigate(R.id.action_messageFragment_to_blankFragment,bundle)
        dialog?.dismiss()
    }

    fun showPrg(){
        binding.prg.visibility = View.VISIBLE
        binding.appCompatButton.isEnabled = false

    }

    fun hidePrg(){
        binding.prg.visibility = View.GONE
        binding.appCompatButton.isEnabled = true

    }


    private fun observer() {

        ticketViwModel.ticketModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    dialog?.hide()
                    uploadOrder.globalFile.clear()
                    uploadOrder.localFile.clear()
                    uploadOrder.notifyDataSetChanged()
                    replacement0()
                    binding.description.setText("")
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    dialog?.hide()

                }
            }
        }
    }
}