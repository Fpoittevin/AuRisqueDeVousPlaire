package com.poittevin.francois.aurisquedevousplaire.ui.reductionDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.poittevin.francois.aurisquedevousplaire.R
import com.poittevin.francois.aurisquedevousplaire.databinding.FragmentDialogReductionBinding
import com.poittevin.francois.aurisquedevousplaire.injection.Injection

class ReductionDialogFragment: DialogFragment() {

    private lateinit var binding: FragmentDialogReductionBinding
    private val reductionViewModel: ReductionViewModel by activityViewModels {
        Injection.provideViewModelFactory(requireContext())
    }
    private lateinit var reductionButtonClickListener: ReductionButtonClickListener

    companion object {
        @JvmStatic
        fun newInstance(
            reductionButtonClickListener: ReductionButtonClickListener
        ) = ReductionDialogFragment().apply {
            this.reductionButtonClickListener = reductionButtonClickListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_reduction, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = reductionViewModel

            fragmentDialogReductionUseButton.setOnClickListener {
                reductionButtonClickListener.onReductionButtonClick()
            }
            fragmentDialogReductionCancelButton.setOnClickListener {
                requireDialog().cancel()
            }
        }

        return binding.root
    }

    interface ReductionButtonClickListener {
        fun onReductionButtonClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reductionViewModel.initValues()
    }
}