package com.sudansh.deliveries

import android.arch.core.executor.testing.CountingTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sudansh.deliveries.repository.local.db.AppDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class DeliveryDaoTest : KoinTest {

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    private lateinit var _db: AppDatabase
    private val db: AppDatabase
        get() = _db

    @Before
    fun initDb() {
        _db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }

    @Test
    fun testSaveAndFind() {
        val dao = db.deliveryDao()
        val entities = createListDelivery("foo", 123.0, 456.0, "bar", 10)

        // Save entities
        dao.insert(entities)

        // Keep id for each one
        val ids = entities.map { it.id }

        // Request one entity per id
        val requestedEntities = ids.map { dao.findDeliveryById(it).liveValue() }

        // compare result
        assertEquals(entities, requestedEntities)
    }

    @Test
    fun testFindAllBy() {
        val dao = db.deliveryDao()
        val entity = createDelivery(1, "foo", 123.0, 456.0, "bar")
        dao.insert(entity)
        val entity2 = createDelivery(2, "fooo", 1234.0, 4567.0, "baar")
        dao.insert(entity2)

        val resultList = dao.findDeliveryById(2).liveValue()

        assertEquals(entity2, resultList)
    }

}
