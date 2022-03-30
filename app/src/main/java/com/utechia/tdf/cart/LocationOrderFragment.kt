package com.utechia.tdf.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.enum.MainEnum
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
    private var location = ""
    private var floor = ""
    private val rooms:MutableList<String> = mutableListOf()
    private val floors:MutableSet<String> = mutableSetOf()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.radioButton.isChecked = true
        binding.radioButton2.isChecked = false
        binding.radioButton3.isChecked = false
        binding.editText.apply {
          //  this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
            this.isEnabled = false
            this.text .clear()

        }

        binding.autoCompleteTextView.apply {
//            this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            this.isEnabled = false
            this.text.clear()

        }
        binding.textinput.isEnabled = false

        binding.autoCompleteTextView2.apply {
//            this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            this.isEnabled = false
            this.text.clear()

        }
        binding.textinput2.isEnabled = false

        type = 0
        location = prefs.getString(MainEnum.Location.main,"").toString()
        floor = prefs.getString(MainEnum.Floor.main,"").toString()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)

        binding.myOffice.text = prefs.getString(MainEnum.Location.main,"").toString()

        officeViewModel.getOffice()

        binding.radioButton.setOnClickListener {
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = false
            location = prefs.getString(MainEnum.Location.main,"").toString()
            type = 0
            binding.editText.apply {
             //   this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text .clear()

            }
            binding.textinput.isEnabled = false

            binding.autoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()
            }
            binding.textinput2.isEnabled = false

            binding.autoCompleteTextView2.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()
            }
        }

        binding.radioButton2.setOnClickListener {
            binding.radioButton.isChecked = false
            binding.radioButton3.isChecked = false
            type = 1
            binding.editText.apply {
               // this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text .clear()

            }
            binding.autoCompleteTextView.apply {
//                this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                this.isEnabled = true
                this.text.clear()

            }
            binding.textinput.isEnabled = true

            binding.textinput2.isEnabled = false

            binding.autoCompleteTextView2.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()
            }

        }

        binding.radioButton3.setOnClickListener {
            binding.radioButton2.isChecked = false
            binding.radioButton.isChecked = false
            binding.autoCompleteTextView.apply {
                //this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.disActive))
                this.isEnabled = false
                this.text.clear()

            }
            location = binding.editText.text.toString()
            type = 2
            binding.editText.apply {
                this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                this.isEnabled = true
            }
            binding.textinput.isEnabled = false

            binding.autoCompleteTextView2.apply {
                this.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                this.isEnabled = true
                this.text.clear()

            }
            binding.textinput2.isEnabled = true


        }

        binding.appCompatButton.setOnClickListener {
            when(type){
                0 -> {
                    location = prefs.getString(MainEnum.Location.main,"").toString()
                    floor = prefs.getString(MainEnum.Floor.main,"").toString()
                }
                1 -> {
                    location = binding.autoCompleteTextView.text.toString()
                }
                2 -> {
                    location = binding.editText.text.toString()
                    floor = binding.autoCompleteTextView2.text.toString()
                }
            }

            when {
                location.isEmpty() -> {
                    Toast.makeText(context,"No location selected",Toast.LENGTH_SHORT).show()
                }
                floor.isEmpty() -> {
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

                    it.data.map { it1 ->
                        it1.location?.let { it2 -> rooms.add(it2) }
                    }

                    it.data.map { it1 ->
                        it1.floor?.let { it2 -> floors.add(it2) }
                    }

                    autoCompleteAdapter = AutoCompleteAdapter(requireActivity(),R.layout.dropdown_item,R.id.textItem,rooms)
                    binding.autoCompleteTextView.setAdapter(autoCompleteAdapter)

                    autoCompleteAdapter = AutoCompleteAdapter(requireActivity(),R.layout.dropdown_item,R.id.textItem,floors.toMutableList())
                    binding.autoCompleteTextView2.setAdapter(autoCompleteAdapter)



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