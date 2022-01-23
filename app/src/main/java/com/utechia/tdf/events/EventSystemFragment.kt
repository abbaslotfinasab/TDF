package com.utechia.tdf.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.utechia.tdf.databinding.FragmentEventSystemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventSystemFragment : Fragment() {

    private lateinit var binding: FragmentEventSystemBinding
/*
    private val eventViewModel:EventViewModel by viewModels()
*/
    private val eventAdapter:EventAdapter = EventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*
        eventViewModel.getEventList("PastEvents")
*/


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

               /* when(tab?.position){

                    0 -> {
                        eventViewModel.getEventList("PastEvents")
                        observer()
                    }
                    1 -> {
                        eventViewModel.getEventList("CurrentEvents")
                        observer()
                    }
                    2 -> {
                        eventViewModel.getEventList("UpComming")
                        observer()
                    }
                }*/
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                /*when(tab?.position){

                    0 -> {
                        eventViewModel.getEventList("PastEvents")
                        observer()
                    }
                    1 -> {
                        eventViewModel.getEventList("CurrentEvents")
                        observer()
                    }
                    2 -> {
                        eventViewModel.getEventList("UpComming")
                        observer()
                    }
                }*/
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

               /* when(tab?.position){

                    0 -> {
                        eventViewModel.getEventList("PastEvents")
                        observer()
                    }
                    1 -> {
                        eventViewModel.getEventList("CurrentEvents")
                        observer()
                    }
                    2 -> {
                        eventViewModel.getEventList("UpComming")
                        observer()
                    }
                }*/
            }

        })

     binding.recyclerView.apply {
         adapter = eventAdapter
         layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
         addItemDecoration(EventItemDecoration())
     }
/*
        observer()
*/

    }

  /*  private fun observer() {

        eventViewModel.event.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE

                    if (it.data.size!=0){
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyLayout.visibility = View.GONE
                        eventAdapter.addData(it.data)


                    }
                    else{
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                    }

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyLayout.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyLayout.visibility = View.VISIBLE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/
}