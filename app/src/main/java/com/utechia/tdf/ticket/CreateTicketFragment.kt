package com.utechia.tdf.ticket

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateTicketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTicketFragment : Fragment() {

    private lateinit var binding: FragmentCreateTicketBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTicketBinding.inflate(inflater, container, false)
        val floor = resources.getStringArray(R.array.floor_array)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,floor)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.category.setOnClickListener {
            findNavController().navigate(R.id.action_createTicketFragment_to_categoryFragment)
        }

        binding.appCompatButton.setOnClickListener {
            findNavController().navigate(R.id.action_createTicketFragment_to_ticketConfirmationFragment)
        }

        binding.btnUpload.setOnClickListener {

            findNavController().navigate(R.id.action_createTicketFragment_to_uploadFragment)


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                // Use Uri object instead of File to avoid storage permissions
                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show()

            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}