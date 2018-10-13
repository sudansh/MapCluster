package com.sudansh.deliveries.ui

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sudansh.deliveries.R
import com.sudansh.deliveries.databinding.ItemAtmBinding
import com.sudansh.deliveries.repository.local.db.entity.Atm


class DeliverAdapter(private var callback: OnItemClickListener) : RecyclerView.Adapter<DeliverViewHolder>() {
    private val listDelivery: MutableList<Atm> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliverViewHolder {
        val binding = DataBindingUtil.inflate<ItemAtmBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_atm,
                parent,
                false
        )
        return DeliverViewHolder(binding)
    }

    override fun getItemCount() = listDelivery.size

    override fun onBindViewHolder(vh: DeliverViewHolder, position: Int) {
        val binding = vh.binding()
        binding.data = listDelivery[position]
        binding.mainContainer.setOnClickListener {
        }
        binding.executePendingBindings()
    }

    fun updateItems(data: List<Atm>) {
        val diffResult = DiffUtil.calculateDiff(DeliverDiffUtil(data, listDelivery))
        listDelivery.clear()
        listDelivery.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }
}

class DeliverViewHolder(private val binding: ItemAtmBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun binding() = binding
}

interface OnItemClickListener {
    fun onItemClick(delivery: Atm, image: ImageView, description: TextView)
}
