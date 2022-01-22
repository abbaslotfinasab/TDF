package com.utechia.tdf.refreshment

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
import com.utechia.domain.model.ItemModel
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    val cartViewModel:CartViewModel by viewModels()
    private val checkOutViewModel:CheckOutViewModel by viewModels()
    private val cartAdapter:CartAdapter = CartAdapter(this)
    private var food: MutableList<ItemModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        cartViewModel.getCart()

        binding.appCompatButton.setOnClickListener {
            checkOutViewModel.checkoutCart()
            checkObserver()
        }

        binding.appBackButton.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.recyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(ItemDecorationCart())

            ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val bundle = bundleOf("foodId" to food[viewHolder.layoutPosition].food.id)
                    findNavController().navigate(R.id.action_cartFragment_to_deleteFragment,bundle)
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
                            ContextCompat.getDrawable(context,R.drawable.ic_push_delete)!!
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

    fun deleteItem(id:Int,size:Int){

        cartViewModel.deleteItem(id)
        if(size<2) {
            binding.appCompatButton.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.emptyLayout.visibility = View.VISIBLE
            binding.appBackButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun checkObserver() {
        checkOutViewModel.orderModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {

                    binding.prg.visibility = View.GONE
                    binding.appCompatButton.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.emptyLayout.visibility = View.GONE
                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun observer() {

        cartViewModel.cartModel.observe(viewLifecycleOwner) {

            when (it) {
                is Result.Success -> {
                    binding.prg.visibility = View.GONE

                    if (it.data.size!=0){
                        binding.appCompatButton.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyLayout.visibility = View.GONE
                        cartAdapter.addData(it.data[0].items!!)
                        food.addAll(it.data[0].items!!)

                    }
                    else{
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                        binding.appCompatButton.visibility = View.GONE
                    }

                }

                is Result.Loading -> {
                    binding.prg.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.prg.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}