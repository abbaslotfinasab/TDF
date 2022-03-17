package com.utechia.tdf.qrcode

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.utechia.domain.enum.MainEnum
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentQrCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrCodeFragment : Fragment() {

    private lateinit var binding: FragmentQrCodeBinding
    private lateinit var multiFormatWriter: MultiFormatWriter
    private lateinit var matrix: BitMatrix
    private lateinit var barcodeEncoder: BarcodeEncoder
    private lateinit var bitmap: Bitmap
    private lateinit var prefs: SharedPreferences
    private var employeeId = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences(MainEnum.Tdf.main, Context.MODE_PRIVATE)

        employeeId = prefs.getInt(MainEnum.EmployeeId.main , 0).toString()

        multiFormatWriter = MultiFormatWriter()

        matrix = multiFormatWriter.encode(employeeId,BarcodeFormat.QR_CODE,resources.getDimensionPixelSize(
            R.dimen.qrCode),resources.getDimensionPixelSize(
            R.dimen.qrCode))

        barcodeEncoder = BarcodeEncoder()

        bitmap = barcodeEncoder.createBitmap(matrix)

        binding.qrCode.setImageBitmap(bitmap)

    }

}