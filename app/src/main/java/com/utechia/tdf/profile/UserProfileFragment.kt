package com.utechia.tdf.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.utechia.tdf.databinding.FragmentProfileBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.utechia.domain.enum.MainEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: UserProfileViewModel by viewModels()
    private lateinit var prefs: SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)

        binding.profilePicture.bringToFront()

        profileViewModel.getProfile()

        observer()
    }

    private fun observer() {
        profileViewModel.profile.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE
                    binding.name.text = it.data.name
                    binding.email.text = it.data.mail
                    binding.job.text = it.data.jobTitle
                    with(prefs.edit()) {
                        it.data.employeeId?.let { it1 -> putInt(MainEnum.EmployeeId.main, it1) }
                        it.data.officeLocation?.let { it2 -> putString(MainEnum.Location.main, it2) }
                        it.data.workStation?.let { it3 -> putString(MainEnum.WorkStation.main, it3) }
                        it.data.floor?.let { it4 -> putString(MainEnum.Floor.main, it4) }
                        it.data.id?.let { it5 -> putInt(MainEnum.UserId.main, it5) }

                    }?.apply()
                    Glide.with(requireActivity())
                        .load(it.data.profilePictureModel?.url)
                        .centerCrop()
                        .placeholder(R.drawable.ic_profile_icon)
                        .error(R.drawable.ic_profile_icon)
                        .into(binding.profilePicture)
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