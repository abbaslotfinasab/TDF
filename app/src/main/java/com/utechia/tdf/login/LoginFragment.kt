package com.utechia.tdf.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.utechia.tdf.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val verifyViewModel: VerifyViewModel by viewModels()
    private lateinit var prefs: SharedPreferences
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
        prefs = requireActivity().getSharedPreferences("tdf", Context.MODE_PRIVATE)



        if (arguments != null) {
            code = requireArguments().getString("code", "")
            arguments?.clear()
        }

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
            code = ""

        }

        observer()

    }

    private fun verifyObserver() {

        verifyViewModel.verifyModel.observe(viewLifecycleOwner){

            when(it){

                is Result.Success ->{
                    binding.prg.visibility = View.VISIBLE
                    if (it.data.isTeaBoy == false) {
                        with(prefs.edit()){
                            putString("name",it.data.name)
                            putString("job",it.data.jobTitle)
                            putString("USER_ID",it.data.userHomeId)
                            putBoolean("Start",true)

                        }.apply()
                        findNavController().navigate(R.id.action_loginFragment_to_userhomeFragment)

                    }

                    else {
                        with(prefs.edit()){
                            putString("name",it.data.name)
                            putString("floor", it.data.floor.toString())
                            putBoolean("isTeaBoy", it.data.isTeaBoy == true)
                            putBoolean("isTeaBoyActive", it.data.isTeaBoyActive == true)
                            putString("USER_ID",it.data.userHomeId)
                            putBoolean("Start",true)

                        }.apply()
                        findNavController().navigate(R.id.action_loginFragment_to_teaBoyHomeFragment)


                    }

                }

                is Result.Loading ->{
                    binding.prg.visibility = View.VISIBLE
                    binding.appCompatButton.isEnabled = false
                }

                is Result.Error ->{
                    binding.appCompatButton.isEnabled = true
                    findNavController().navigate(R.id.action_loginFragment_to_authenticationFragment)
                }
            }
        }
    }

    private fun observer() {

        loginViewModel.loginModel.observe(viewLifecycleOwner) {

            when (it) {

                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.appCompatButton.isEnabled = true
                    uri = it.data.url.toString()

                }

                is Result.Loading -> {
                    binding.appCompatButton.isEnabled = false
                    binding.prg.visibility = View.VISIBLE

                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.appCompatButton.isEnabled = true
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}