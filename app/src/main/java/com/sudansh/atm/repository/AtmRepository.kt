package com.sudansh.atm.repository

import android.arch.lifecycle.LiveData
import com.sudansh.atm.data.ApiResponse
import com.sudansh.atm.data.AppExecutors
import com.sudansh.atm.data.NetworkBoundResource
import com.sudansh.atm.data.Resource
import com.sudansh.atm.repository.local.db.dao.AtmDao
import com.sudansh.atm.repository.local.db.entity.Atm
import com.sudansh.atm.repository.remote.api.AtmApi
import com.sudansh.atm.testing.OpenForTesting

@OpenForTesting
open class AtmRepository(
        val appExecutors: AppExecutors,
        val atmDao: AtmDao,
        val api: AtmApi
) {

    fun getAtms(): LiveData<Resource<List<Atm>>> {
        return object :
                NetworkBoundResource<List<Atm>, List<Atm>>(appExecutors) {
            override fun saveCallResult(item: List<Atm>) {
                atmDao.insert(item)
            }

            override fun shouldFetch(data: List<Atm>?): Boolean =
                    data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<Atm>> =
                    atmDao.findAll()

            override fun createCall(): LiveData<ApiResponse<List<Atm>>> =
                    api.getAtms()
        }.asLiveData()
    }
}