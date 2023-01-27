package com.example.navigationsample.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.navigationsample.R
import com.example.navigationsample.base.BaseBottomSheetFragment
import com.example.navigationsample.databinding.FragmentSampleBottomSheetBinding
import com.example.navigationsample.fragments.HomeFragment.Companion.MILLIS_MAX
import com.example.navigationsample.utils.CountDownTimer

class SampleBottomSheetFragment : BaseBottomSheetFragment(R.layout.fragment_sample_bottom_sheet) {
    private val binding by viewBinding(FragmentSampleBottomSheetBinding::bind)
    private val timer = CountDownTimer(MILLIS_MAX)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer.start(viewLifecycleOwner) {
            binding.textViewCounter.text = it
        }

        binding.buttonAddAnotherFragment.setOnClickListener {
            findNavController().navigate(R.id.action_bottomSheetSampleFragment_self)
        }

        arguments?.getString(R.string.arg_name)?.let {
            binding.nameText.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.stop()
    }

    private fun Bundle.getString(@StringRes resId: Int): String? {
        return getString(context?.getString(resId))
    }
}