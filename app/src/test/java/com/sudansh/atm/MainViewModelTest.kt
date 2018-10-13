package com.sudansh.atm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sudansh.atm.data.Resource
import com.sudansh.atm.repository.AtmRepository
import com.sudansh.atm.repository.local.db.entity.Atm
import com.sudansh.atm.ui.MapsViewModel
import com.sudansh.atm.util.mock
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
    lateinit var repo: AtmRepository
    @Mock
    lateinit var results: Observer<Resource<List<Atm>>>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = MapsViewModel(repo)
        viewModel.listAtm.observeForever(results)
    }

    @Test
    fun testInitCall() {
        viewModel.listAtm.observeForever(mock())
        //verify getAtms is called with false
        verify(repo).getAtms()
    }

}