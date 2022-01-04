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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentMessageBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MessageFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMessageBinding
    val uploadOrder:UploadReplyAdapter = UploadReplyAdapter(this)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onResume() {
        super.onResume()
        if (uploadOrder.file.size !=0){
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

        binding.recyclerView.apply {
            adapter = uploadOrder
            layoutManager = GridLayoutManager(context,calculateNoOfColumns(context, 100.0F))
        }

        binding.appCompatButton.setOnClickListener {
            dialog?.dismiss()
            uploadOrder.file.clear()
            binding.description.setText("")
        }

        binding.btnUpload.setOnClickListener {
            showUpload()
        }
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

}