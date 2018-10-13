package com.sudansh.atm.repository.local.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

@Entity
data class Atm(@PrimaryKey var sonectId: String = "",
               @Embedded var address: Address,
               var name: String = "",
               var latitude: String = "",
               var longitude: String = "") : ClusterItem {
    override fun getSnippet() = ""

    override fun getTitle() = ""

    override fun getPosition() = LatLng(latitude.toDouble(), longitude.toDouble())
}

data class Address(var formatted: String = "")