package com.utechia.tdf.health

import android.os.Bundle
import android.util.Log
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.utechia.domain.enum.HealthEnum
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentHealthChildBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HealthChildFragment(val health: String) : Fragment() {

    private lateinit var binding: FragmentHealthChildBinding
    private val healthViewModel:HealthViewModel by viewModels()
    private val healthAdapter :HealthAdapter = HealthAdapter()
    private lateinit var barDateSet :BarDataSet
    private val yValues : ArrayList<BarEntry> = arrayListOf()
    private val dataSets:ArrayList<IBarDataSet> = arrayListOf()
    private lateinit var barData:BarData
    private var currentDayOfMonth = 0
    private var currentDayOfWeek = 0
    private var firstDayOfWeek = ""
    private var lastDayOfWeek = ""
    private var firstDayOfMonth = ""
    private var lastDayOfMonth = ""
    private var startTime = ""
    private var endTime = ""
    private var dayOfWeek = 0f
    private var dayOfMonth = 0f
    private lateinit var dw : List<String>
    private lateinit var customBarChartRenderer: RoundedBarChartRenderer
    private lateinit var chartMarkerView: ChartMarkerView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dw = requireActivity().resources.getStringArray(R.array.week).toList()

        customBarChartRenderer = RoundedBarChartRenderer(binding.chart,binding.chart.animator,binding.chart.viewPortHandler,10f)
        chartMarkerView = ChartMarkerView(context,R.layout.item_marker,binding.chart.xAxis.valueFormatter)

        currentDayOfWeek = LocalDateTime.now().dayOfWeek.value

        currentDayOfMonth = LocalDateTime.now().dayOfMonth

        startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        endTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()


        endTime = LocalDateTime.now().minusDays(currentDayOfWeek.toLong()).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()


        firstDayOfWeek = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).with(ChronoField.DAY_OF_WEEK,1).minusDays(1).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        lastDayOfWeek = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).with(ChronoField.DAY_OF_WEEK,7).minusDays(1).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        firstDayOfMonth = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).withDayOfMonth(1).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        lastDayOfMonth = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays((LocalDateTime.MAX.dayOfMonth-currentDayOfMonth).toLong()).atZone(ZoneId.systemDefault()).toOffsetDateTime().withOffsetSameInstant(
            ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()

        when(health){

            HealthEnum.Daily.health -> {
                healthViewModel.getTop(startTime,endTime)
                binding.chartLayout.visibility = View.GONE

                binding.refreshLayout.setOnRefreshListener {
                    healthViewModel.getTop(startTime,endTime)
                }
            }

            HealthEnum.Weekly.health -> {
                healthViewModel.getTop(firstDayOfWeek,lastDayOfWeek)
                healthViewModel.getChart(firstDayOfWeek,lastDayOfWeek)

                binding.refreshLayout.setOnRefreshListener {
                    healthViewModel.getTop(firstDayOfWeek,lastDayOfWeek)
                    healthViewModel.getChart(firstDayOfWeek,lastDayOfWeek)
                }

                for (i in 1 .. 7){
                    yValues.add(BarEntry(i.toFloat(),0f))
                }
                binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(dw)
                designChart()

            }

            HealthEnum.Monthly.health -> {
                healthViewModel.getTop(firstDayOfMonth,lastDayOfMonth)
                healthViewModel.getChart(firstDayOfMonth,lastDayOfMonth)

                for (i in 1..LocalDateTime.MAX.dayOfMonth){
                    yValues.add(BarEntry(i.toFloat(),0f))
                }

                binding.refreshLayout.setOnRefreshListener {
                    healthViewModel.getTop(firstDayOfMonth,lastDayOfMonth)
                    healthViewModel.getChart(firstDayOfMonth,lastDayOfMonth)

                }
                designChart()
            }
        }

        Log.d("firstDayOfWeek",firstDayOfWeek)
        Log.d("lastDayOfWeek",lastDayOfWeek)

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

        healthViewModel.chartModel.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    binding.refreshLayout.isRefreshing = false
                    yValues.clear()

                    when(health){

                        HealthEnum.Weekly.health -> {

                            for (i in 0 until it.data.size){
                                dayOfWeek = OffsetDateTime.parse(it.data[i].UpdatedAt).atZoneSameInstant(
                                    ZoneId.systemDefault()
                                ).toLocalDateTime().dayOfWeek.plus(1).value.toFloat()
                                yValues.add(BarEntry(dayOfWeek,it.data[i].count?.toFloat()?:0f))
                                Log.d("dayOfWeek",dayOfWeek.toString())

                            }
                            binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(dw)
                            binding.chart.marker = chartMarkerView

                        }

                        HealthEnum.Monthly.health -> {

                            for (i in 0 until it.data.size){
                                dayOfMonth = OffsetDateTime.parse(it.data[i].UpdatedAt).atZoneSameInstant(
                                    ZoneId.systemDefault()
                                ).toLocalDateTime().dayOfMonth.toFloat()

                                yValues.add(BarEntry(dayOfMonth,it.data[i].count?.toFloat()?:0f))
                                binding.chart.marker = chartMarkerView

                            }
                        }
                    }
                    designChart()
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

    private fun designChart(){

        barDateSet = BarDataSet(yValues,"")
        barDateSet.apply {
            color = ContextCompat.getColor(requireActivity(), R.color.lineChart)
            setDrawValues(false)
        }
        dataSets.add(barDateSet)
        barData = BarData(dataSets)
        binding.chart.data = barData
        binding.chart.notifyDataSetChanged()
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
        binding.chart.renderer = customBarChartRenderer
        binding.chart.invalidate()
    }
}