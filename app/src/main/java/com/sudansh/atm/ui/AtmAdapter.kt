package com.sudansh.atm.ui

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.sudansh.atm.R
import com.sudansh.atm.databinding.ItemAtmBinding
import com.sudansh.atm.repository.local.db.entity.Atm
import com.sudansh.atm.util.toDistance

class AtmAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<AtmViewHolder>() {
    private val items: MutableList<Atm> = mutableListOf()
    private var myLocation: LatLng? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtmViewHolder {
        val binding = DataBindingUtil.inflate<ItemAtmBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_atm,
                parent,
                false
        )
        return AtmViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(vh: AtmViewHolder, position: Int) {
        val binding = vh.binding()
        val atm = items[position]
        binding.address.text = atm.address.formatted
        binding.name.text = atm.name
        myLocation?.let {
            binding.distance.text = SphericalUtil.computeDistanceBetween(it, atm.position).toDistance()
        }
    }

    fun updateItems(data: List<Atm>) {
        val diffResult = DiffUtil.calculateDiff(AtmDiffUtil(data, items))
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setCurrentLocation(myLocation: LatLng) {
        this.myLocation = myLocation
        notifyDataSetChanged()
    }
}

class AtmViewHolder(private val binding: ItemAtmBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    fun binding() = binding
}