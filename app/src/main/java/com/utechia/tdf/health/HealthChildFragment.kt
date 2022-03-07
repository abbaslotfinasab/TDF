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
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
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
    private lateinit var  xAxis : XAxis
    private lateinit var week : Array<String>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        week = activity?.resources?.getStringArray(R.array.week)?: arrayOf("")
        healthViewModel.getTop()


        if(health != HealthEnum.Daily.health){

            yValues.add(Entry(0f,300f))
            yValues.add(Entry(1f,100f))
            yValues.add(Entry(2f,200f))
            yValues.add(Entry(3f,400f))
            yValues.add(Entry(4f,0f))
            yValues.add(Entry(5f,0f))
            yValues.add(Entry(6f,0f))

            lineDateSet = LineDataSet(yValues,"")
            lineDateSet.apply {
                fillAlpha = 110
                color = ContextCompat.getColor(requireActivity(), R.color.lineChart)
                lineWidth = 2f
                setDrawFilled(true)
                fillDrawable = ContextCompat.getDrawable(requireActivity(),R.drawable.fade_chart)
                mode = LineDataSet.Mode.CUBIC_BEZIER
                setDrawValues(false)
            }

            dataSets.add(lineDateSet)

            lineData = LineData(dataSets)
            binding.chart.data = lineData

            binding.chart.isDragEnabled = true
            binding.chart.setScaleEnabled(false)
            binding.chart.axisLeft.setDrawLabels(false)
            binding.chart.axisLeft.setDrawLimitLinesBehindData(false)
            binding.chart.axisRight.setDrawLimitLinesBehindData(false)
            binding.chart.axisRight.setDrawLabels(false)
            binding.chart.xAxis.setDrawAxisLine(false)
            binding.chart.axisLeft.setDrawAxisLine(false)
            binding.chart.axisRight.setDrawAxisLine(false)
            binding.chart.axisLeft.setDrawGridLines(false)
            binding.chart.axisRight.setDrawGridLines(false)
            binding.chart.xAxis.setDrawGridLines(false)
            binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            binding.chart.legend.form = Legend.LegendForm.NONE
            binding.chart.description.isEnabled = false
            binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(week?.toList())


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