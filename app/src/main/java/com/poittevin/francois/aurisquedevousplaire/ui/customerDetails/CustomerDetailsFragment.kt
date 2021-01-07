package com.poittevin.francois.aurisquedevousplaire.ui.customerDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentCustomerDetailsBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection

class CustomerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCustomerDetailsBinding

    private val customerDetailsViewModel: CustomerDetailsViewModel by activityViewModels {
        Injection.provideViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
           val customerId = it.getInt(CUSTOMER_ID_KEY)
            customerDetailsViewModel.getCustomer(customerId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_details, container, false)
        binding.apply {
            lifecycleOwner = this@CustomerDetailsFragment
            viewModel = customerDetailsViewModel
        }
        return binding.root
    }

    companion object {
        const val CUSTOMER_ID_KEY = "CUSTOMER_ID"

        @JvmStatic
        fun newInstance(customerId: Int) =
            CustomerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(CUSTOMER_ID_KEY, customerId)
                }
            }
    }
}