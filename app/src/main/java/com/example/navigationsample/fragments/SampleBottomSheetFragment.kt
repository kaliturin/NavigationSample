package com.example.navigationsample.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.navigationsample.R
import com.example.navigationsample.base.BaseBottomSheetFragment
import com.example.navigationsample.databinding.FragmentSampleBottomSheetBinding
import com.example.navigationsample.fragments.HomeFragment.Companion.MILLIS_MAX
import com.example.navigationsample.utils.CountDownTimer

class SampleBottomSheetFragment : BaseBottomSheetFragment(R.layout.fragment_sample_bottom_sheet) {
    private val binding by viewBinding(FragmentSampleBottomSheetBinding::bind)
    private val timer = CountDownTimer(MILLIS_MAX)
    private val args: SampleBottomSheetFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer.start(viewLifecycleOwner) {
            binding.textViewCounter.text = it
        }

        binding.buttonAddAnotherFragment.setOnClickListener {
            findNavController().navigate(R.id.action_bottomSheetSampleFragment_self)
        }

        args.name?.let { binding.nameText.text = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.stop()
    }
}