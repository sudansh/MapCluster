package com.sudansh.atm.di

import androidx.room.Room
import com.sudansh.atm.data.AppExecutors
import com.sudansh.atm.repository.AtmRepository
import com.sudansh.atm.repository.local.db.AppDatabase
import org.koin.dsl.module.applicationContext


val localModule = applicationContext {
    bean { AppExecutors() }
    bean { AtmRepository(get(), get(), get()) }
    bean {
        Room.databaseBuilder(get(), AppDatabase::class.java, "atm-db").build()
    }
    bean { get<AppDatabase>().atmDao() }
}