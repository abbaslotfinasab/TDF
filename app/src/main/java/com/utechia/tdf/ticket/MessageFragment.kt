package com.utechia.tdf.ticket

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentMessageBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MessageFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMessageBinding
    private val uploadOrder:UploadReplyAdapter = UploadReplyAdapter()



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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appCompatButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnUpload.setOnClickListener {
            findNavController().navigate(R.id.action_messageFragment_to_uploadFragment)

        }
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
        binding.uploadLayout0.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

    }

    fun deleteItem(position:Int){
        uploadOrder.file.removeAt(position)
        uploadOrder.notifyItemRemoved(position)

        if(uploadOrder.file.isEmpty()){
            replacement()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                // Use Uri object instead of File to avoid storage permissions
                binding.uploadLayout0.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                uploadOrder.addData(uri)

            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
        }
    }

}