package com.utechia.tdf.ticket

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utechia.domain.model.ticket.CategoryModel
import com.utechia.tdf.R
import com.utechia.tdf.databinding.FragmentCategoryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class CategoryFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCategoryFragmentBinding
    private lateinit var navHostFragment : NavHostFragment
    private val categoryAdapter:CategoryAdapter = CategoryAdapter(this)
    private var categoryList:ArrayList<CategoryModel> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryFragmentBinding.inflate(inflater, container, false)

        if(dialog !=null && dialog?.window !=null){
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            categoryList =
                (requireArguments().getSerializable("category")) as ArrayList<CategoryModel>
        }

        navHostFragment = requireActivity().supportFragmentManager.fragments[0] as NavHostFragment

        binding.recyclerView.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            categoryAdapter.addData(categoryList)
            addItemDecoration(CategoryItemDecoration())
        }



    }
    fun setCategory(title:String,mId: Int){

        val parent = navHostFragment.childFragmentManager.primaryNavigationFragment as CreateTicketFragment
        parent.selectCategory(title,mId)
        dialog?.dismiss()

    }
}