package com.sudansh.atm.ui

import android.support.v7.util.DiffUtil
import com.sudansh.atm.repository.local.db.entity.Atm

class AtmDiffUtil(private val newList: List<Atm>, private val oldList: List<Atm>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            newList[newItemPosition].sonectId == oldList[oldItemPosition].sonectId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            newList[newItemPosition] == oldList[oldItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size
}