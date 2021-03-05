package com.poittevin.francois.aurisquedevousplaire.ui.customerDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentCustomerDetailsBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection
import com.poittevin.francois.aurisquedevousplaire.ui.reductionDialog.ReductionDialogFragment

class CustomerDetailsFragment : Fragment(), ReductionDialogFragment.ReductionButtonClickListener {

    private lateinit var binding: FragmentCustomerDetailsBinding
    private lateinit var customerModificationFabListener: CustomerModificationFabListener
    private lateinit var customerDeleteButtonListener: CustomerDeleteButtonListener
    private var customerId: Int? = null
    private val reductionDialogFragment = ReductionDialogFragment.newInstance(this)

    private val customerDetailsViewModel: CustomerDetailsViewModel by activityViewModels {
        Injection.provideViewModelFactory(requireContext())
    }

    companion object {
        const val CUSTOMER_ID_KEY = "CUSTOMER_ID"

        @JvmStatic
        fun newInstance(
            customerId: Int,
            customerModificationFabListener: CustomerModificationFabListener,
            customerDeleteButtonListener: CustomerDeleteButtonListener
        ) =
            CustomerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(CUSTOMER_ID_KEY, customerId)
                }
                this.customerModificationFabListener = customerModificationFabListener
                this.customerDeleteButtonListener = customerDeleteButtonListener

                Log.e("customerDetails", "NEW INSTANCE")
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            customerId = bundle.getInt(CUSTOMER_ID_KEY)
            customerId?.let {
                customerDetailsViewModel.getCustomer(it)
            }
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
                customerId?.let {
                    customerModificationFabListener.onCustomerModificationFabClick(it)
                }
            }
        }
        configureReductionButton()
        configureDeleteButton()

        return binding.root
    }

    private fun configureReductionButton() {
        binding.fragmentCustomerDetailsUseReductionButton.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            reductionDialogFragment.show(fragmentTransaction, "reduction")
        }
    }

    private fun configureDeleteButton() {
        binding.fragmentCustomerDetailsDeleteButton.setOnClickListener {
            customerDetailsViewModel.deleteCustomer()
            customerDeleteButtonListener.onCustomerDeleteButtonClick()
        }
    }

    interface CustomerDeleteButtonListener {
        fun onCustomerDeleteButtonClick()
    }

    interface CustomerModificationFabListener {
        fun onCustomerModificationFabClick(customerId: Int)
    }

    override fun onReductionButtonClick() {
        reductionDialogFragment.dismiss()
        customerDetailsViewModel.useReduction()
    }
}