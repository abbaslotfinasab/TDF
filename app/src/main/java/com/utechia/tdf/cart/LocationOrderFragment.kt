package com.utechia.tdf.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.model.cart.OfficeModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentLocationOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationOrderFragment : Fragment() {

    private lateinit var binding: FragmentLocationOrderBinding
    private val officeViewModel: OfficeViewModel by viewModels()
    private lateinit var autoCompleteAdapter: AutoCompleteAdapter
    private lateinit var prefs: SharedPreferences
    private var type = 0
    private var floorId = 0
    private var location = ""
    private var floor = -1
    private val locations:MutableList<OfficeModel> = mutableListOf()
    private val rooms:MutableList<String> = mutableListOf()
    private val floorsName:MutableList<String> = mutableListOf()
    private val floorsId:ArrayList<Int> = arrayListOf()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.myRadioButton.isChecked = true
        binding.selectRadioButton.isChecked = false
        binding.otherRadioButton.isChecked = false
        binding.addressInput.apply {
          //  this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
            this.isEnabled = false
            this.text .clear()

        }

        binding.floorSelectLocationInputLayout.isEnabled = false

        binding.floorSelectLocationAutoCompleteTextView.apply {
            //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
            this.isEnabled = false
            this.text.clear()
        }

        binding.selectLocationAutoCompleteTextView.apply {
//            this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            this.isEnabled = false
            this.text.clear()

        }
        binding.selectLocationLayout.isEnabled = false

        binding.otherAutoCompleteTextView.apply {
//            this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            this.isEnabled = false
            this.text.clear()

        }
        binding.otherTextInputLayout.isEnabled = false

        type = 0
        location = "test"
        floor = 10


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)

        binding.myOffice.text = prefs.getString(MainEnum.Location.main,"").toString()

        officeViewModel.getOffice()

        binding.myRadioButton.setOnClickListener {
            binding.selectRadioButton.isChecked = false
            binding.otherRadioButton.isChecked = false
            location = prefs.getString(MainEnum.Location.main,"").toString()
            type = 0
            binding.addressInput.apply {
             //   this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text .clear()

            }

            binding.selectLocationAutoCompleteTextView.setAdapter(null)

            binding.floorSelectLocationInputLayout.isEnabled = false

            binding.floorSelectLocationAutoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()
            }

            binding.selectLocationLayout.isEnabled = false

            binding.selectLocationAutoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()
            }
            binding.otherTextInputLayout.isEnabled = false

            binding.otherAutoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()
            }
        }

        binding.selectRadioButton.setOnClickListener {
            binding.myRadioButton.isChecked = false
            binding.otherRadioButton.isChecked = false
            type = 1
            binding.addressInput.apply {
               // this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text .clear()
            }

            binding.floorSelectLocationInputLayout.isEnabled = true

            binding.floorSelectLocationAutoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = true
            }

            binding.selectLocationAutoCompleteTextView.apply {
//                this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                this.isEnabled = true

            }
            binding.selectLocationLayout.isEnabled = true

            binding.otherTextInputLayout.isEnabled = false

            binding.otherAutoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()
            }

        }

        binding.otherRadioButton.setOnClickListener {
            binding.selectRadioButton.isChecked = false
            binding.myRadioButton.isChecked = false
            binding.selectLocationAutoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()

            }

            location = binding.addressInput.text.toString()
            type = 2
            binding.addressInput.apply {
                this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                this.isEnabled = true
            }

            binding.selectLocationAutoCompleteTextView.setAdapter(null)

            binding.floorSelectLocationInputLayout.isEnabled = false

            binding.floorSelectLocationAutoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()
            }

            binding.selectLocationLayout.isEnabled = false

            binding.selectLocationAutoCompleteTextView.apply {
//                this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                this.isEnabled = false
                this.text.clear()

            }

            binding.otherAutoCompleteTextView.apply {
//                this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                this.isEnabled = true

            }
            binding.otherTextInputLayout.isEnabled = true
        }


        binding.floorSelectLocationAutoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                floorId = position
                collectLocation(parent.getItemAtPosition(position).toString())
            }

        binding.otherAutoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                floorId = position
            }

        binding.appCompatButton.setOnClickListener {
            when(type){
                0 -> {
                    location = prefs.getString(MainEnum.Location.main,"").toString()
                    floor = prefs.getInt(MainEnum.FloorId.main,-1)
                }
                1 -> {
                    location = binding.selectLocationAutoCompleteTextView.text.toString()
                    floor = floorsId[floorId]
                }

                2 -> {
                    location = binding.addressInput.text.toString()
                    floor = floorsId[floorId]
                }
            }

            when {
                location.isEmpty() -> {
                    Toast.makeText(context,"No location selected",Toast.LENGTH_SHORT).show()
                }
                floor==-1-> {
                    Toast.makeText(context,"No floor selected",Toast.LENGTH_SHORT).show()

                }

                else -> {
                    val bundle =
                        bundleOf(MainEnum.Location.main to location, MainEnum.Floor.main to floor)
                    findNavController().navigate(
                        R.id.action_locationOrderFragment_to_orderFragment,
                        bundle
                    )
                }
            }
        }

        observer()
    }

    private fun observer() {

        officeViewModel.officeModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE

                    locations.addAll(it.data)

                    floorsName.clear()
                    it.data.map { it1 ->
                        if (it1.isDeleted == false && it1.id !=null) {
                            it1.name?.let { it2 -> floorsName.add(it2) }
                        }
                    }

                    floorsId.clear()
                    it.data.map { it1 ->
                        if (it1.isDeleted == false && it1.id !=null) {
                            it1.id?.let { it2 -> floorsId.add(it2) }
                        }
                    }

                    autoCompleteAdapter = AutoCompleteAdapter(requireActivity(),R.layout.dropdown_item,R.id.textItem,floorsName)
                    binding.otherAutoCompleteTextView.setAdapter(autoCompleteAdapter)
                    binding.floorSelectLocationAutoCompleteTextView.setAdapter(autoCompleteAdapter)

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

    private fun collectLocation(floor:String){

        binding.selectLocationAutoCompleteTextView.text.clear()

        rooms.clear()
        locations.map { it1 ->
            if (it1.isDeleted == false && it1.name == floor) {
                it1.locations?.map {
                    if(it.active==true)
                    it.name?.let { it2 -> rooms.add(it2) }
                }
            }
        }
        autoCompleteAdapter = AutoCompleteAdapter(requireActivity(),R.layout.dropdown_item,R.id.textItem,rooms)
        binding.selectLocationAutoCompleteTextView.setAdapter(autoCompleteAdapter)

    }
}