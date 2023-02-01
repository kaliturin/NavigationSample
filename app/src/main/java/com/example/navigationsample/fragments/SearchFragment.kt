package com.example.navigationsample.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.navigationsample.R
import com.example.navigationsample.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNavigateToFragment.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToUpLevelFragment(
                    title = binding.editTextTitle.text.toString(),
                    withParentToolbar = true,
                    withParentBottomMenu = true,
                )
            )
        }

        binding.buttonNavigateToFragment2.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToUpLevelFragment(
                    title = binding.editTextTitle.text.toString()
                )
            )
        }
    }
}