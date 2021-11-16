package com.utechia.tdf.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.activity.MainActivity
import com.utechia.tdf.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val verifyViewModel: VerifyViewModel by viewModels()

    private var uri = ""
    private var code = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments != null)
            code = requireArguments().getString("code", "")

        binding.appCompatButton.setOnClickListener {

            if (uri != "") {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                ContextCompat.startActivity(requireContext(), browserIntent, null)
            }else
                Toast.makeText(context,"Please try later",Toast.LENGTH_SHORT).show()

        }

        if (code != ""){
            verifyViewModel.verify(code)
            verifyObserver()

        }

        observer()

    }

    private fun verifyObserver() {

        verifyViewModel.verifyModel.observe(viewLifecycleOwner){

            when(it){

                is Result.Success ->{
                    binding.prg.visibility = View.GONE

                    if (it.data.isTeaBoy == false) {
                        (activity as MainActivity).setupUser()
                        findNavController().navigate(R.id.action_loginFragment_to_userhomeFragment)
                    }

                    else {
                        (activity as MainActivity).setupTeaBoy()
                        findNavController().navigate(R.id.action_loginFragment_to_teaBoyHomeFragment)
                    }

                }

                is Result.Loading ->{
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error ->{
                    findNavController().navigate(R.id.action_loginFragment_to_authenticationFragment)
                }
            }
        }

    }

    private fun observer() {

        loginViewModel.loginModel.observe(viewLifecycleOwner) {

            when (it) {

                is Result.Success -> {

                    uri = it.data.url.toString()

                }

                is Result.Loading -> {}

                is Result.Error -> {

                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}