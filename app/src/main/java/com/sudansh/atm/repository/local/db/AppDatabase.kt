package com.sudansh.atm.repository.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sudansh.atm.repository.local.db.dao.AtmDao
import com.sudansh.atm.repository.local.db.entity.Atm


@Database(entities = [Atm::class],
        exportSchema = false,
        version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun atmDao(): AtmDao
}