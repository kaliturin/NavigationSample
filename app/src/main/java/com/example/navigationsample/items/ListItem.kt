package com.example.navigationsample.items

import android.view.View
import com.example.navigationsample.R
import com.example.navigationsample.databinding.ListItemBinding
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class ListItem(
    val name: String,
    private val onClickListener: (ListItem) -> Unit
) : BindableItem<ListItemBinding>() {

    override fun initializeViewBinding(view: View) = ListItemBinding.bind(view)

    override fun getLayout() = R.layout.list_item

    override fun isSameAs(other: Item<*>) = other is ListItem && name == other.name
    override fun hasSameContentAs(other: Item<*>) = other is ListItem && name == other.name

    override fun bind(viewBinding: ListItemBinding, position: Int) {
        viewBinding.name.text = name
        viewBinding.root.setOnClickListener {
            onClickListener(this)
        }
    }
}
