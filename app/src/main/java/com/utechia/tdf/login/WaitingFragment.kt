package com.utechia.tdf.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentWaitingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitingFragment : Fragment() {

    private lateinit var binding: FragmentWaitingBinding
    private val verifyViewModel: VerifyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {

            val code = requireArguments().getString("code", "")
            verifyViewModel.verify(code)

        }

        binding.appCompatButton.isEnabled = false

        observer()
    }

    private fun observer() {
        verifyViewModel.verifyModel.observe(viewLifecycleOwner){

            when(it){

                is Result.Success ->{
                    binding.prg.visibility = View.GONE
                    findNavController().navigate(R.id.action_waitingFragment_to_homeFragment)
                }

                is Result.Loading ->{
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error ->{
                    findNavController().navigate(R.id.action_waitingFragment_to_authenticationFragment)
                }
            }
        }
    }
}