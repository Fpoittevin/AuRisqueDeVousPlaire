package com.poittevin.francois.aurisquedevousplaire.ui.customerForm

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentCustomerFormBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection
import com.poittevin.francois.aurisquedevousplaire.models.Customer
import com.poittevin.francois.aurisquedevousplaire.ui.customerDetails.CustomerDetailsFragment
import com.poittevin.francois.aurisquedevousplaire.utils.ContactChoice
import com.poittevin.francois.aurisquedevousplaire.utils.Utils
import org.joda.time.LocalDate

class CustomerFormFragment : Fragment() {

    private lateinit var binding: FragmentCustomerFormBinding
    private lateinit var customerSaveListener: CustomerSaveListener

    private val customerFormViewModel: CustomerFormViewModel by activityViewModels {
        Injection.provideViewModelFactory(requireContext())
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
            customerFormViewModel.customer = MutableLiveData<Customer>().apply {
                value = Customer()
            }
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
            fragmentCustomerFormSaveFab.setOnClickListener {

                customerFormViewModel.saveCustomer()
                customerFormViewModel.saveResult.observe(viewLifecycleOwner) {
                    if(it) {
                        customerFormViewModel.customer.value?.id?.let { customerId ->

                            customerSaveListener.onCustomerSave(
                                customerId
                            )
                        }
                    }
                }
            }
        }
        configureBirthdayButton()
        configureContactChoiceRadioGroup()

        return binding.root
    }

    private fun configureContactChoiceRadioGroup() {
        binding.fragmentCustomerFormContactChoiceRadioGroup.setOnCheckedChangeListener { _, radioId ->
            customerFormViewModel.customer.value?.let {
                it.contactChoice =
                    when (radioId) {
                        R.id.fragment_customer_form_email_radio -> ContactChoice.EMAIL
                        R.id.fragment_customer_form_sms_radio -> ContactChoice.SMS
                        else -> ContactChoice.NOTHING
                    }
            }
        }
    }

    private fun configureBirthdayButton() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, dayOfMonth: Int ->
                val birthdayTimestamp = Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                customerFormViewModel.customer.value?.birthdayTimestamp = birthdayTimestamp
                binding.fragmentCustomerFormBirthdayButton.text =
                    Utils.convertTimestampToStringDate(birthdayTimestamp)
            },
            LocalDate.now().year,
            (LocalDate.now().monthOfYear - 1),
            LocalDate.now().dayOfMonth
        )
        binding.fragmentCustomerFormBirthdayButton.setOnClickListener {
            datePickerDialog.show()
        }
    }

    interface CustomerSaveListener {
        fun onCustomerSave(customerId: Int)
    }
}