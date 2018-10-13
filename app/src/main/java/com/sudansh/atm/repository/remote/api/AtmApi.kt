package com.sudansh.atm.repository.remote.api

import androidx.lifecycle.LiveData
import com.sudansh.atm.data.ApiResponse
import com.sudansh.atm.repository.local.db.entity.Atm
import retrofit2.http.GET
import retrofit2.http.Url


interface AtmApi {

    @GET()
    fun getAtms(@Url url: String = "data/ATM_20181005_DEV.json"): LiveData<ApiResponse<List<Atm>>>

}