package com.sudansh.deliveries

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.sudansh.deliveries.data.Resource
import com.sudansh.deliveries.repository.DeliveryRepository
import com.sudansh.deliveries.repository.local.db.dao.DeliveryDao
import com.sudansh.deliveries.repository.local.db.entity.Atm
import com.sudansh.deliveries.repository.remote.api.DeliverApi
import com.sudansh.deliveries.util.InstantAppExecutors
import com.sudansh.deliveries.util.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class DeliveryRepoTest {
    private val dao = mock(DeliveryDao::class.java)
    private val api = mock(DeliverApi::class.java)
    private val repo = DeliveryRepository(InstantAppExecutors(), dao, api)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadUser() {
        repo.getDeliveries()
        verify(dao).getDeliveries()
    }

    @Test
    fun testNetworkCall() {
        val dbData = MutableLiveData<List<Atm>>()
        `when`(dao.getDeliveries()).thenReturn(dbData)
        val call = ApiUtil.successCall(createListDelivery("foo", 123.0, 456.0, "bar", 10))
        `when`(api.getDeliveries()).thenReturn(call)
        val observer = mock<Observer<Resource<List<Atm>>>>()

        //fetch data from db
        repo.getDeliveries(false).observeForever(observer)

        //verify no api is called
        verify(api, never()).getDeliveries()

        val updatedDbData = MutableLiveData<List<Atm>>()
        `when`(dao.getDeliveries()).thenReturn(updatedDbData)
        dbData.value = null

        //force fetch from api
        repo.getDeliveries(true).observeForever(observer)

        //verify api is called
        verify(api, times(1)).getDeliveries()
    }
}