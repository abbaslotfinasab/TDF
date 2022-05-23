package com.utechia.tdf.reservation

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.utechia.domain.model.profile.ProfileModel
import com.utechia.domain.model.profile.ProfilePictureModel
import com.utechia.tdf.databinding.FragmentAddGuestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGuestFragment : Fragment() {

    private lateinit var binding: FragmentAddGuestBinding
    private lateinit var invitePeopleFragment: InvitePeopleFragment
    private var name = ""
    private var job = ""
    private var email = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddGuestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        invitePeopleFragment = this.parentFragment as InvitePeopleFragment

        binding.appCompatButton.setOnClickListener {
            name = binding.inputName.text.toString()
            email = binding.inputEmail.text.toString()
            job = binding.inputJob.text.toString()

            when{
                name.isEmpty() -> {
                    Toast.makeText(context,"No value found for name",Toast.LENGTH_SHORT).show()
                }
                !isValidEmail(email) -> {
                    Toast.makeText(context,"Email is incorrect",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    InvitationListener.addGuestListener.postValue(
                        ProfileModel(name,0,"test","test",job,email,1,"test", ProfilePictureModel("test","test","test"),true))
                    invitePeopleFragment.dismiss()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}


