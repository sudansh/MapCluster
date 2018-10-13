package com.sudansh.atm.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sudansh.atm.data.Resource
import com.sudansh.atm.repository.AtmRepository
import com.sudansh.atm.repository.local.db.entity.Atm
import com.sudansh.atm.testing.OpenForTesting

@OpenForTesting
class MapsViewModel(private val repo: AtmRepository) : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var listAtm: LiveData<Resource<List<Atm>>>

    init {
        fetch()
    }

    private fun fetch() {
        listAtm = repo.getAtms()
    }
}