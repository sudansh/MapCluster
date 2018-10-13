package com.sudansh.deliveries.repository.local.db.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

@Entity
data class Atm(@PrimaryKey var sonectId: String = "",
               @Embedded var address: Address,
               var imagePath: String = "",
               var latitude: String = "",
               var name: String = "",
               var longitude: String = "") : ClusterItem {
    override fun getSnippet() = ""

    override fun getTitle() = ""

    override fun getPosition() = LatLng(latitude.toDouble(), longitude.toDouble())
}

data class Address(var formatted: String = "")