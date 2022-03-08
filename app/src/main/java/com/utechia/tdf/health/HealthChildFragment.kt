package com.utechia.tdf.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.utechia.domain.enum.HealthEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentHealthChildBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthChildFragment(val health: String) : Fragment() {

    private lateinit var binding: FragmentHealthChildBinding
    private val healthViewModel:HealthViewModel by viewModels()
    private val healthAdapter :HealthAdapter = HealthAdapter()
    private lateinit var barDateSet :BarDataSet
    private val yValues : ArrayList<BarEntry> = arrayListOf()
    private val dataSets:ArrayList<IBarDataSet> = arrayListOf()
    private lateinit var barData:BarData



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        healthViewModel.getTop()


        if(health != HealthEnum.Daily.health){

            yValues.add(BarEntry(1f,300f))
            yValues.add(BarEntry(2f,100f))
            yValues.add(BarEntry(3f,200f))
            yValues.add(BarEntry(4f,400f))
            yValues.add(BarEntry(5f,0f))
            yValues.add(BarEntry(6f,0f))
            yValues.add(BarEntry(7f,0f))

            barDateSet = BarDataSet(yValues,"")
            barDateSet.apply {
                color = ContextCompat.getColor(requireActivity(), R.color.lineChart)
                setDrawValues(false)
            }

            dataSets.add(barDateSet)

            barData = BarData(dataSets)
            binding.chart.data = barData

            binding.chart.isDragEnabled = true
            binding.chart.setScaleEnabled(false)
            binding.chart.axisLeft.setDrawLabels(true)
            binding.chart.axisLeft.setDrawLimitLinesBehindData(false)
            binding.chart.axisRight.setDrawLimitLinesBehindData(false)
            binding.chart.axisRight.setDrawLabels(false)
            binding.chart.xAxis.setDrawAxisLine(true)
            binding.chart.xAxis.axisLineWidth = 1f
            binding.chart.xAxis.axisLineColor = ContextCompat.getColor(requireActivity(),R.color.black)
            binding.chart.axisLeft.setDrawAxisLine(true)
            binding.chart.axisLeft.axisLineWidth = 1f
            binding.chart.axisLeft.axisLineColor = ContextCompat.getColor(requireActivity(),R.color.black)
            binding.chart.axisRight.setDrawAxisLine(false)
            binding.chart.axisLeft.setDrawGridLines(false)
            binding.chart.axisRight.setDrawGridLines(false)
            binding.chart.xAxis.setDrawGridLines(false)
            binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            binding.chart.legend.form = Legend.LegendForm.NONE
            binding.chart.description.isEnabled = false
            binding.chart.axisLeft.axisMinimum = 0f

        }else
            binding.chartLayout.visibility = View.GONE


        binding.recyclerView.apply {
            adapter = healthAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(HealthItemDecoration())
        }


        observer()
    }

    private fun observer(){
        healthViewModel.topHealthModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    healthAdapter.addData(it.data)
                }

                is Result.Loading -> {
                }

                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}