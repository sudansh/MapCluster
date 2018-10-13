package com.sudansh.deliveries.repository.remote.api

import android.arch.lifecycle.LiveData
import com.sudansh.deliveries.data.ApiResponse
import com.sudansh.deliveries.repository.local.db.entity.Atm
import retrofit2.http.GET
import retrofit2.http.Url


interface DeliverApi {

    @GET()
    fun getDeliveries(@Url url: String = "data/ATM_20181005_DEV.json"): LiveData<ApiResponse<List<Atm>>>

}