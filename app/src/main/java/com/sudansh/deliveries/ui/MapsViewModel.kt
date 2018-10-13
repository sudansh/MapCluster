package com.sudansh.deliveries.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.sudansh.deliveries.data.Resource
import com.sudansh.deliveries.repository.DeliveryRepository
import com.sudansh.deliveries.repository.local.db.entity.Atm
import com.sudansh.deliveries.testing.OpenForTesting
import com.sudansh.deliveries.util.switch

@OpenForTesting
class MapsViewModel(private val repo: DeliveryRepository) : ViewModel() {
    private val refresh: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading = ObservableBoolean(true)
    val deliveries: LiveData<Resource<List<Atm>>> =
            refresh.switch { startLoad ->
                repo.getDeliveries(startLoad)
            }

    init {
        refresh.value = false
    }

    fun refresh() {
        refresh.value = true
    }
}