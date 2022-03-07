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
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
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
    private lateinit var lineDateSet :LineDataSet
    private val yValues : ArrayList<Entry> = arrayListOf()
    private val dataSets:ArrayList<ILineDataSet> = arrayListOf()
    private lateinit var lineData:LineData



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

        binding.refreshLayout.setOnRefreshListener {
            healthViewModel.getTop()

        }

        if(health != HealthEnum.Daily.health){

            yValues.add(Entry(0f,300f))
            yValues.add(Entry(1f,100f))
            yValues.add(Entry(2f,200f))
            yValues.add(Entry(3f,400f))
            yValues.add(Entry(4f,200f))
            yValues.add(Entry(5f,500f))
            yValues.add(Entry(6f,600f))

            lineDateSet = LineDataSet(yValues,"My Steps")
            lineDateSet.apply {
                fillAlpha = 110
                color = ContextCompat.getColor(requireActivity(), R.color.lineChart)
                lineWidth = 2f
                setDrawFilled(true)
                fillDrawable = ContextCompat.getDrawable(requireActivity(),R.drawable.fade_chart)
            }

            dataSets.add(lineDateSet)

            lineData = LineData(dataSets)
            binding.chart.data = lineData

            binding.chart.isDragEnabled = true
            binding.chart.setScaleEnabled(false)
            binding.chart.axisLeft.setDrawGridLines(false)
            binding.chart.axisRight.setDrawGridLines(false)
            binding.chart.xAxis.setDrawGridLines(false)



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
                    binding.refreshLayout.isRefreshing = false
                    healthAdapter.addData(it.data)
                }

                is Result.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                }

                is Result.Error -> {
                    binding.refreshLayout.isRefreshing = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}