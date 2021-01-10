package com.poittevin.francois.aurisquedevousplaire.ui.customerForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentCustomerFormBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.ui.customerDetails.CustomerDetailsFragment

class CustomerFormFragment : Fragment() {

    private lateinit var binding: FragmentCustomerFormBinding
    private lateinit var customerSaveListener: CustomerSaveListener

    private val customerFormViewModel: CustomerFormViewModel by activityViewModels {
        Injection.provideViewModelFactory()
    }

    companion object {

        @JvmStatic
        fun newInstance(
            customerId: Int?,
            customerSaveListener: CustomerSaveListener
        ) =
            CustomerFormFragment().apply {
                customerId?.let {
                    arguments = Bundle().apply {
                        putInt(CustomerDetailsFragment.CUSTOMER_ID_KEY, customerId)
                    }
                }
                this.customerSaveListener = customerSaveListener
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val customerId = it.getInt(CustomerDetailsFragment.CUSTOMER_ID_KEY)
            customerFormViewModel.getCustomer(customerId)
        } ?: run {
            customerFormViewModel.customer.value = Customer()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_form, container, false)
        binding.apply {
            lifecycleOwner = this@CustomerFormFragment
            viewModel = customerFormViewModel
            fragmentCustomerFormProgressBar.visibility = View.GONE
            fragmentCustomerDetailsModificationFab.setOnClickListener {

                fragmentCustomerFormProgressBar.visibility = View.VISIBLE
                customerFormViewModel.saveCustomer()
                customerFormViewModel.result.observe(viewLifecycleOwner) {
                    customerFormViewModel.result.value?.id?.let { customerId ->

                        customerSaveListener.onCustomerSave(
                            customerId
                        )
                    }
                }


            }
        }

        return binding.root
    }

    interface CustomerSaveListener {
        fun onCustomerSave(customerId: Int)
    }
}