package com.utechia.tdf.notification

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private  val notificationViewModel:NotificationViewModel by viewModels()
    private val notificationAdapter:NotificationAdapter = NotificationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationViewModel.getNotification()

        binding.refreshLayout.setOnRefreshListener {

            notificationViewModel.getNotification()

        }



        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = notificationAdapter
            addItemDecoration(ItemNotificationDecoration())

            binding.appBackButton.setOnClickListener {
                findNavController().navigate(R.id.action_notificationFragment_self)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val bundle = bundleOf("nId" to notificationAdapter.notification[viewHolder.layoutPosition].id)
                    findNavController().navigate(R.id.action_notificationFragment_to_notificationDeleteFragment,bundle)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        val itemView : View = viewHolder.itemView
                        val icon : Drawable =
                            ContextCompat.getDrawable(context, R.drawable.ic_push_delete)!!
                        val top = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                        val left = itemView.width - icon.intrinsicWidth - (itemView.height - icon.intrinsicHeight) / 6
                        val right = left + icon.intrinsicHeight
                        val bottom = top + icon.intrinsicHeight

                        if (dX < 0){
                            icon.setBounds(left,top,right,bottom)
                        }
                        else if (dX > 0){
                            icon.setBounds(left,top,top,bottom)

                        }
                        icon.draw(c)
                    }
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX/3,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }).attachToRecyclerView(binding.recyclerView)
        }

        observer()
    }

    private fun observer() {
        notificationViewModel.notificationModel.observe(viewLifecycleOwner){

            when (it) {
                is Result.Success -> {
                    binding.refreshLayout.isRefreshing = false
                    binding.prg.visibility = View.GONE

                    if (it.data.size!=0){
                        binding.recyclerView.visibility = View.VISIBLE
                        notificationAdapter.addData(it.data)
                    }else {
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                    }
                }

                is Result.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                    binding.prg.visibility = View.GONE
                    binding.emptyLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE

                }

                is Result.Error -> {
                    binding.refreshLayout.isRefreshing = false
                    binding.prg.visibility = View.GONE
                    binding.emptyLayout.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}