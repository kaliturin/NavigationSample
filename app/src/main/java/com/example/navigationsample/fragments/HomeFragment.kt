package com.example.navigationsample.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.navigationsample.R
import com.example.navigationsample.databinding.FragmentHomeBinding
import com.example.navigationsample.utils.CountDownTimer

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // try to restore timer's state
        val millsLeft = savedInstanceState?.getLong(MILLS_NAME) ?: MILLIS_MAX
        timer = CountDownTimer(millsLeft)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(MILLS_NAME, timer.millisLeft)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer.start(viewLifecycleOwner) {
            binding.textViewCounter.text = it
        }

        binding.buttonAddFragment.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottomSheetSampleFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.stop()
    }

    companion object {
        const val MILLIS_MAX = 20 * 60 * 1000L // 20 Min
        const val MILLS_NAME = "millisLeft"
    }
}
