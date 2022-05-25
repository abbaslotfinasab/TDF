package com.utechia.tdf.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utechia.domain.utile.Result
import com.utechia.tdf.databinding.FragmentSearchPeopleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPeopleFragment : Fragment() {

    private lateinit var binding: FragmentSearchPeopleBinding
    private val invitationViewModel:InvitationViewModel by viewModels()
    private val invitationAdapter:InvitationAdapter = InvitationAdapter()
    private lateinit var invitePeopleFragment: InvitePeopleFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        invitePeopleFragment = this.parentFragment as InvitePeopleFragment


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    invitationViewModel.getUserList(query)
                }else{
                    invitationAdapter.userList.clear()
                    invitationAdapter.notifyDataSetChanged()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    invitationViewModel.getUserList(newText)
                }else{
                    invitationAdapter.userList.clear()
                    invitationAdapter.notifyDataSetChanged()
                }
                return false
            }
        })

        binding.inviteRecycler.apply {
            adapter = invitationAdapter
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL,false)
        }

        binding.btnSelect.setOnClickListener {
            invitePeopleFragment.dismiss()
        }

        observer()
    }
     fun observer(){
        invitationViewModel.profileModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    invitationAdapter.addData(it.data)
                }
                is Result.Loading -> {}

                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

