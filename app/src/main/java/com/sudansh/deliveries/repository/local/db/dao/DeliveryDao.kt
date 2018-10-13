package com.sudansh.deliveries.repository.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sudansh.deliveries.repository.local.db.entity.Atm

@Dao
interface DeliveryDao {

    @Query("SELECT * FROM atm")
    fun getDeliveries(): LiveData<List<Atm>>

    @Query("SELECT * FROM atm WHERE sonectId= :sonectId")
    fun findDeliveryById(sonectId: Int): LiveData<Atm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(atm: Atm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Atm>)
}