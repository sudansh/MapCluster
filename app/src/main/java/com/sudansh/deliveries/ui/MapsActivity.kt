package com.sudansh.deliveries.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.sudansh.deliveries.R
import com.sudansh.deliveries.data.Resource
import com.sudansh.deliveries.data.Status.*
import com.sudansh.deliveries.databinding.ActivityMapsBinding
import com.sudansh.deliveries.repository.local.db.entity.Atm
import com.sudansh.deliveries.util.observeNonNull
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.ext.android.inject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ClusterManager.OnClusterClickListener<Atm>, ClusterManager.OnClusterItemClickListener<Atm> {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var mClusterManager: ClusterManager<Atm>? = null
    private val vm: MapsViewModel  by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mClusterManager = ClusterManager(this, mMap)
        mClusterManager?.setOnClusterClickListener(this)
        mClusterManager?.setOnClusterItemClickListener(this)
        mClusterManager?.renderer = MapRenderer(this, mMap, mClusterManager)

        vm.deliveries.observeNonNull(this) {
            updateAtms(it)
        }

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        } else
            mMap.isMyLocationEnabled = true
    }

    private fun updateAtms(resource: Resource<List<Atm>>) {
        when (resource.status) {
            SUCCESS -> {
                buildMarkers(resource.data.orEmpty())
                progressBar.visibility = View.GONE
            }
            LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ERROR -> {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun buildMarkers(list: List<Atm>) {
        val bounds = LatLngBounds.Builder()
        list.forEach { bounds.include(it.position) }
        mClusterManager?.let {
            it.clearItems()
            it.addItems(list)
            it.cluster()
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 30))
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }

    override fun onClusterItemClick(p0: Atm?) = true

    override fun onClusterClick(p0: Cluster<Atm>?) = true

    companion object {
        const val REQUEST_LOCATION = 1000
    }
}
