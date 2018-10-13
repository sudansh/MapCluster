package com.sudansh.atm

import com.sudansh.atm.repository.local.db.entity.Address
import com.sudansh.atm.repository.local.db.entity.Atm

fun createAtm(id: String, name: String, lat: Double, lng: Double): Atm {
    return Atm(id, Address("London"), name, lat.toString(), lng.toString())
}

fun createAtmList(description: String, lat: Double, lng: Double, count: Int = 10): List<Atm> {
    return (1 until count).map {
        Atm(it.toString(), Address("London"), description + it, lat.toString() + it, lng.toString() + it)
    }
}