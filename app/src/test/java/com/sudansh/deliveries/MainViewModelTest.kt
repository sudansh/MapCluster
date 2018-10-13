package com.sudansh.deliveries

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.sudansh.deliveries.data.Resource
import com.sudansh.deliveries.repository.DeliveryRepository
import com.sudansh.deliveries.repository.local.db.entity.Atm
import com.sudansh.deliveries.ui.MapsViewModel
import com.sudansh.deliveries.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MapsViewModel
    @Mock
    lateinit var repo: DeliveryRepository
    @Mock
    lateinit var results: Observer<Resource<List<Atm>>>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = MapsViewModel(repo)
        viewModel.deliveries.observeForever(results)
    }

    @Test
    fun testRefresh() {
        viewModel.deliveries.observeForever(mock())
        viewModel.refresh()
        //verify getDeliveries is called with true
        verify(repo).getDeliveries(true)
    }

    @Test
    fun testInitCall() {
        viewModel.deliveries.observeForever(mock())
        //verify getDeliveries is called with false
        verify(repo).getDeliveries(false)
    }

}