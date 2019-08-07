package com.sudansh.atm.di

import androidx.room.Room
import com.sudansh.atm.data.AppExecutors
import com.sudansh.atm.repository.AtmRepository
import com.sudansh.atm.repository.local.db.AppDatabase
import org.koin.dsl.module

val localModule = module {
	single { AppExecutors() }
	single { AtmRepository(get(), get(), get()) }
	single { Room.databaseBuilder(get(), AppDatabase::class.java, "atm-db").build() }
	single { get<AppDatabase>().atmDao() }
}