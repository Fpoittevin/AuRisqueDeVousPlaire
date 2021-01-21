package com.poittevin.francois.aurisquedevousplaire.ui.message

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentDialogMessageBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection

class MessageDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogMessageBinding
    private val messageViewModel: MessageViewModel by activityViewModels {
        Injection.provideViewModelFactory()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MessageDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_message, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = messageViewModel
            fragmentDialogMessageSendButton.setOnClickListener {
                messageViewModel.minMax.value?.get(0)?.let { it1 -> Log.e("values", it1.toString()) }
                messageViewModel.minMax.value?.get(1)?.let { it1 -> Log.e("values", it1.toString()) }
            }
        }

        return binding.root
    }
}