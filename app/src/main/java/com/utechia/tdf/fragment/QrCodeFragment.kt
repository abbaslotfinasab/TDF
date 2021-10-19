package com.utechia.tdf.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentQrCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrCodeFragment : Fragment() {

    private lateinit var binding: FragmentQrCodeBinding
    private var count = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scanCode.setOnClickListener {
            count +=1
            if (count % 2 ==0){
                it.background = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_stop_qr)
            }
            else {
                it.background = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_qr_start)
            }

        }

    }

}