package com.poittevin.francois.aurisquedevousplaire.ui.customersList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentCustomersListBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection

class CustomersListFragment : Fragment() {

    private lateinit var binding: FragmentCustomersListBinding
    private val customersListViewModel: CustomersListViewModel by activityViewModels {
        Injection.provideViewModelFactory()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CustomersListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customers_list, container, false)
        configureRecyclerView()

        return binding.root
    }

    private fun configureRecyclerView() {
        val customersAdapter = CustomersAdapter(requireActivity() as CustomersAdapter.CustomerItemClickCallback)
        binding.fragmentCustomersListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = customersAdapter
        }
        customersListViewModel.getCustomersList().observe(viewLifecycleOwner, {
            customersAdapter.updateCustomersList(it)
        })
    }
}