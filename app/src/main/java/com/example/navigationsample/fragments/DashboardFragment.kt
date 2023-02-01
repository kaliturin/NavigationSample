package com.example.navigationsample.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.navigationsample.R
import com.example.navigationsample.databinding.FragmentDashboardBinding
import com.example.navigationsample.items.ListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val binding by viewBinding(FragmentDashboardBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReplaceFragment.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToUpLevelFragment(
                    withParentToolbar = true,
                    withParentBottomMenu = true,
                    title = "Replaced fragment"
                )
            )
        }

        binding.recyclerView.apply {
            adapter = GroupAdapter<GroupieViewHolder>().apply { update(buildItems()) }
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun buildItems(): List<ListItem> {
        return (0..25).map {
            ListItem("Title$it") { item ->
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToBottomSheetSampleFragment(
                        item.name
                    )
                )
            }
        }
    }
}