package com.sudansh.atm.repository.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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