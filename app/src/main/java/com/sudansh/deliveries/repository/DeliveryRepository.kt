package com.sudansh.deliveries.repository

import android.arch.lifecycle.LiveData
import com.sudansh.deliveries.data.ApiResponse
import com.sudansh.deliveries.data.AppExecutors
import com.sudansh.deliveries.data.NetworkBoundResource
import com.sudansh.deliveries.data.Resource
import com.sudansh.deliveries.repository.local.db.dao.DeliveryDao
import com.sudansh.deliveries.repository.local.db.entity.Atm
import com.sudansh.deliveries.repository.remote.api.DeliverApi
import com.sudansh.deliveries.testing.OpenForTesting

@OpenForTesting
class DeliveryRepository(
        val appExecutors: AppExecutors,
        val deliveryDao: DeliveryDao,
        val api: DeliverApi
) {

    fun getDeliveries(isFetch: Boolean = false): LiveData<Resource<List<Atm>>> {
        return object :
                NetworkBoundResource<List<Atm>, List<Atm>>(appExecutors) {
            override fun saveCallResult(item: List<Atm>) {
                deliveryDao.insert(item)
            }

            override fun shouldFetch(data: List<Atm>?): Boolean =
                    isFetch || data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<Atm>> =
                    deliveryDao.getDeliveries()

            override fun createCall(): LiveData<ApiResponse<List<Atm>>> =
                    api.getDeliveries()
        }.asLiveData()
    }
}