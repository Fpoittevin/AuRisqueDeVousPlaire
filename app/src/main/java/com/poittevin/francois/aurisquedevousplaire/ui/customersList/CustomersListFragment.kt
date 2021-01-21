package com.poittevin.francois.aurisquedevousplaire.ui.customersList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentCustomersListBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection

class CustomersListFragment : Fragment() {

    private lateinit var binding: FragmentCustomersListBinding
    private val customersListViewModel: CustomersListViewModel by activityViewModels {
        Injection.provideViewModelFactory()
    }
    lateinit var customersAdapter: CustomersAdapter

    companion object {
        @JvmStatic
        fun newInstance() = CustomersListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customers_list, container, false)
        configureRecyclerView()

        return binding.root
    }

    private fun configureRecyclerView() {
        customersAdapter =
            CustomersAdapter(requireContext(), requireActivity() as CustomersAdapter.CustomerItemClickCallback)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.fragmentCustomersListRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = customersAdapter
            //addItemDecoration(DividerItemDecoration(this.context, linearLayoutManager.orientation))
        }
        updateCustomersList()
    }

    fun updateCustomersList() {
        customersListViewModel.getCustomersList().observe(viewLifecycleOwner, {
            customersAdapter.updateCustomersList(it)
        })
    }
}