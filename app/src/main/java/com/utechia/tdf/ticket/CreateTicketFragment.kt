package com.utechia.tdf.ticket

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.utechia.domain.enum.TicketEnum
import com.utechia.domain.model.ticket.CategoryModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCreateTicketBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateTicketFragment : Fragment() {

    private lateinit var binding: FragmentCreateTicketBinding
    private lateinit var snackbar: Snackbar
    private val ticketViewModel:TicketDetailsViewModel by viewModels()
    private val baseNeedsViewModel:BaseNeedsViewModel by viewModels()
    private val uploadViewModel:UploadViewModel by viewModels()
    private val uploadAdapter:UploadAdapter = UploadAdapter()
    private val locationFloor:MutableList<String> = mutableListOf()
    private val floorId:MutableList<Int> = mutableListOf()
    private val categoryList:ArrayList<CategoryModel> = arrayListOf()
    private var selectedFloor = 0
    private var priority = "Low"
    var category = -1




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,locationFloor)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.segmented.apply{
            when(priority){
                TicketEnum.Low.ticket->{
                    this.check(R.id.lowItem)
                }
                TicketEnum.Medium.ticket->{
                    this.check(R.id.mediumItem)
                }
                TicketEnum.High.ticket->{
                    this.check(R.id.highItem)
                }
            }
        }

        if (uploadAdapter.localFile.size !=0){
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
        baseNeedsViewModel.getNeeds()

        binding.recyclerView.apply {
            adapter = uploadAdapter
            layoutManager = GridLayoutManager(context,calculateNoOfColumns(context, 100.0F))
        }

        binding.segmented.onSegmentChecked {

            when(this.checkedRadioButtonId){
                R.id.highItem -> priority = TicketEnum.High.ticket
                R.id.mediumItem -> priority = TicketEnum.Normal.ticket
                R.id.lowItem -> priority = TicketEnum.Low.ticket
            }
        }

        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ -> selectedFloor = floorId[position]
            }

        binding.category.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("category", categoryList)
            findNavController().navigate(R.id.action_createTicketFragment_to_categoryFragment,bundle)
        }

        binding.appCompatButton.setOnClickListener {


            when{

                binding.title.text.isNullOrEmpty() -> {
                    snackbar = Snackbar.make(view,R.string.titleRequired,Snackbar.LENGTH_SHORT)
                    snackbar.show()

                }

                category ==-1 -> {
                    snackbar = Snackbar.make(view,R.string.categoryRequired,Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }

                binding.description.text.isNullOrEmpty() -> {
                    snackbar = Snackbar.make(view,R.string.descriptionRequired,Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }

                else ->{
                    ticketViewModel.postTicket(

                        binding.description.text.toString(),
                        binding.title.text.toString(),
                        category,
                        priority,
                        selectedFloor,
                        uploadAdapter.mediaUrl,

                        )
                }
            }
        }

        observer()


        binding.uploadLayout0.setOnClickListener {
            findNavController().navigate(R.id.action_createTicketFragment_to_uploadFragment)

        }

        needsObserver()

    }

    private fun needsObserver() {
        baseNeedsViewModel.baseNeedsModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.categoryLayout.visibility = View.VISIBLE
                    binding.titleLayout.visibility= View.VISIBLE
                    binding.priorityLayout.visibility= View.VISIBLE
                    binding.floorLayout.visibility= View.VISIBLE
                    binding.descriptionLayout.visibility= View.VISIBLE
                    binding.uploadLayout.visibility= View.VISIBLE
                    binding.appCompatButton.visibility= View.VISIBLE
                    categoryList.clear()
                    locationFloor.clear()
                    floorId.clear()

                    it.data.ListFloor?.let {it1 -> it1.map {it2 ->
                        it2.name?.let { it3 ->
                        locationFloor.add(
                            it3
                        )
                    } }}


                    it.data.ListFloor?.let {it1 -> it1.map {it2 ->
                        it2.id?.let { it3 ->
                            floorId.add(
                                it3
                            )
                        } }}

                    it.data.category?.let {it1 -> categoryList.addAll(it1) }

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.categoryLayout.visibility = View.GONE
                    binding.titleLayout.visibility= View.GONE
                    binding.priorityLayout.visibility= View.GONE
                    binding.floorLayout.visibility= View.GONE
                    binding.descriptionLayout.visibility= View.GONE
                    binding.uploadLayout.visibility= View.GONE
                    binding.appCompatButton.visibility= View.GONE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.categoryLayout.visibility = View.GONE
                    binding.titleLayout.visibility= View.GONE
                    binding.priorityLayout.visibility= View.GONE
                    binding.floorLayout.visibility= View.GONE
                    binding.descriptionLayout.visibility= View.GONE
                    binding.uploadLayout.visibility= View.GONE
                    binding.appCompatButton.visibility= View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_createTicketFragment_to_ticketConfirmationFragment)


                }
            }
        }
    }

    private fun observer() {
        ticketViewModel.ticketModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.VISIBLE
                    findNavController().navigate(R.id.action_createTicketFragment_to_ticketConfirmationFragment)

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_createTicketFragment_to_ticketConfirmationFragment)


                }
            }
        }
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
        binding.uploadLayout0.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

    }

    fun selectCategory(title:String,mId:Int){
        binding.selectCategory.text = title
        category = mId
    }

    fun deleteItem(position:Int,uri:String){
        uploadAdapter.localFile.removeAt(position)
        uploadAdapter.mediaUrl.remove(uri)
        uploadAdapter.notifyItemRemoved(position)

        if(uploadAdapter.localFile.isEmpty()){
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
                uploadAdapter.addData(uri.toString())
                uploadViewModel.uploadFile(uri)
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
                    binding.prg.visibility = View.GONE
                    uploadAdapter.mediaUrl.add(it.data.path!!)
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}