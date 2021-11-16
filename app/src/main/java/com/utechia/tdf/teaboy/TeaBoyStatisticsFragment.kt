package com.utechia.tdf.teaboy
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utechia.tdf.databinding.FragmentTeaBoyStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeaBoyStatisticsFragment : Fragment() {

    private lateinit var binding: FragmentTeaBoyStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeaBoyStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }

}