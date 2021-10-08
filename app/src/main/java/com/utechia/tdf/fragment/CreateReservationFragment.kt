package com.utechia.tdf.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.tdf.databinding.FragmentCreateReservationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateReservationFragment : Fragment() {

    private lateinit var binding: FragmentCreateReservationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}