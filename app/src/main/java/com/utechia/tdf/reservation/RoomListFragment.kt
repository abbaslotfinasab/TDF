package com.utechia.tdf.reservation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.domain.model.reservation.RoomModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentRoomListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomListFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRoomListBinding
    private val roomViewModel:RoomViewModel by viewModels()
    private val roomAdapter:RoomAdapter = RoomAdapter(this)
    private lateinit var navHostFragment :NavHostFragment
    private lateinit var parent:CreateReservationFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomListBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setOnShowListener{dialog ->
                val d = dialog as BottomSheetDialog
                val bottomSheetInternal: FrameLayout? = d.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                BottomSheetBehavior.from(bottomSheetInternal!!).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return binding.root
    }

    override fun getTheme(): Int = R.style.RoomBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navHostFragment = requireActivity().supportFragmentManager.fragments[0] as NavHostFragment
        parent = navHostFragment.childFragmentManager.primaryNavigationFragment as CreateReservationFragment



        roomViewModel.getRoom()

        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                roomViewModel.getRoom(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                roomViewModel.getRoom(newText)
                return false
            }
        })

        binding.inviteRecycler.apply {
            adapter = roomAdapter
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }
        observer()
    }

    private fun observer(){
        roomViewModel.roomModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    roomAdapter.addData(it.data)
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

