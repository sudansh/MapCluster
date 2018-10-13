package com.sudansh.atm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.sudansh.atm.data.Resource
import com.sudansh.atm.repository.AtmRepository
import com.sudansh.atm.repository.local.db.dao.AtmDao
import com.sudansh.atm.repository.local.db.entity.Atm
import com.sudansh.atm.repository.remote.api.AtmApi
import com.sudansh.atm.util.InstantAppExecutors
import com.sudansh.atm.util.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class AtmRepoTest {
    private val dao = mock(AtmDao::class.java)
    private val api = mock(AtmApi::class.java)
    private val repo = AtmRepository(InstantAppExecutors(), dao, api)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadUser() {
        repo.getAtms()
        verify(dao).findAll()
    }

    @Test
    fun testNetworkCall() {
        val dbData = MutableLiveData<List<Atm>>()
        `when`(dao.findAll()).thenReturn(dbData)
        val call = ApiUtil.successCall(createAtmList("foo", 123.0, 456.0, 10))
        `when`(api.getAtms()).thenReturn(call)
        val observer = mock<Observer<Resource<List<Atm>>>>()

        //fetch data from db
        repo.getAtms().observeForever(observer)

        //verify no api is called
        verify(api, never()).getAtms()

        val updatedDbData = MutableLiveData<List<Atm>>()
        `when`(dao.findAll()).thenReturn(updatedDbData)
        dbData.value = null

        //force fetch from api
        repo.getAtms().observeForever(observer)

        //verify api is called
        verify(api, times(1)).getAtms()
    }
}