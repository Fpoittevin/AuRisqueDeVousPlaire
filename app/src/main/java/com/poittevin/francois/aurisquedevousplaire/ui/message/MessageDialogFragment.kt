package com.poittevin.francois.aurisquedevousplaire.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentDialogMessageBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection
import com.poittevin.francois.aurisquedevousplaire.models.Message

class MessageDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogMessageBinding
    private val messageViewModel: MessageViewModel by activityViewModels {
        Injection.provideViewModelFactory(requireContext())
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
                sendMessage()
                requireDialog().cancel()
            }
            fragmentDialogMessageCancelButton.setOnClickListener {
                requireDialog().cancel()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        messageViewModel.initValues()
    }

    private fun sendMessage() {
        val message = Message().apply {
            withReduction = messageViewModel.withReductionLiveData.value
            messageViewModel.minMaxNumberOfCardsLiveData.value?.get(0)?.let{
                minNumberOfCards = it
            }
            messageViewModel.minMaxNumberOfCardsLiveData.value?.get(1)?.let{
                maxNumberOfCards = it
            }
        }
        messageViewModel.textMessageLiveData.value?.let{
            message.content = it
        }
        messageViewModel.sendMessage(message)
    }
}