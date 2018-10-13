package com.sudansh.deliveries

import com.sudansh.deliveries.repository.local.db.entity.Atm
import com.sudansh.deliveries.repository.local.db.entity.Location

fun createDelivery(id: Int, description: String, lat: Double, lng: Double, url: String): Atm {
    return Atm(id, description, Location(lat, lng, description + "lalamove"), url)
}

fun createListDelivery(description: String, lat: Double, lng: Double, url: String, count: Int = 10): List<Atm> {
    return (1 until count).map {
        Atm(it, description + it, Location(lat, lng, description + "lalamove" + it), url + it)
    }
}