package com.sudansh.atm.repository.local.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sudansh.atm.repository.local.db.entity.Atm

@Dao
interface AtmDao {

    @Query("SELECT * FROM atm")
    fun findAll(): LiveData<List<Atm>>

    @Query("SELECT * FROM atm WHERE sonectId= :sonectId")
    fun findById(sonectId: String): LiveData<Atm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(atm: Atm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Atm>)
}