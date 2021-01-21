package com.poittevin.francois.aurisquedevousplaire.ui.customerDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentCustomerDetailsBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.ui.message.MessageDialogFragment
import com.poittevin.francois.aurisquedevousplaire.ui.reductionDialog.ReductionDialogFragment

class CustomerDetailsFragment : Fragment(), ReductionDialogFragment.ReductionButtonClickListener {

    private lateinit var binding: FragmentCustomerDetailsBinding
    private lateinit var customerModificationFabListener: CustomerModificationFabListener
    private lateinit var reductionChangeListener: ReductionChangeListener
    private lateinit var customer: Customer
    private val reductionDialogFragment = ReductionDialogFragment.newInstance(this)

    private val customerDetailsViewModel: CustomerDetailsViewModel by activityViewModels {
        Injection.provideViewModelFactory()
    }

    companion object {
        const val CUSTOMER_ID_KEY = "CUSTOMER_ID"

        @JvmStatic
        fun newInstance(
            customerId: Int,
            customerModificationFabListener: CustomerModificationFabListener,
            reductionChangeListener: ReductionChangeListener
        ) =
            CustomerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(CUSTOMER_ID_KEY, customerId)
                }
                this.customerModificationFabListener = customerModificationFabListener
                this.reductionChangeListener = reductionChangeListener
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            val customerId = bundle.getInt(CUSTOMER_ID_KEY)
            with(customerDetailsViewModel) {
                getCustomer(customerId)
                customer.observe(this@CustomerDetailsFragment, {
                    this@CustomerDetailsFragment.customer = it
                })
            }
            customerDetailsViewModel.getCustomer(customerId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_details, container, false)
        binding.apply {
            lifecycleOwner = this@CustomerDetailsFragment
            viewModel = customerDetailsViewModel
            fragmentCustomerDetailsModificationFab.setOnClickListener {
                customer.id?.let {
                    customerModificationFabListener.onCustomerModificationFabClick(it)
                }
            }
        }
        configureReductionButton()
        return binding.root
    }

    private fun configureReductionButton() {
        binding.fragmentCustomerDetailsUseReductionButton.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            reductionDialogFragment.show(fragmentTransaction, "reduction")
        }
    }

    interface CustomerModificationFabListener {
        fun onCustomerModificationFabClick(customerId: Int)
    }

    interface ReductionChangeListener {
        fun onReductionChange()
    }

    override fun onReductionButtonClick() {
        reductionDialogFragment.dismiss()
        reductionChangeListener.onReductionChange()
        customerDetailsViewModel.useReduction()
    }
}