package com.sudansh.atm.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.sudansh.atm.R
import com.sudansh.atm.data.Resource
import com.sudansh.atm.data.Status.*
import com.sudansh.atm.repository.local.db.entity.Atm
import com.sudansh.atm.util.hideKeyboard
import com.sudansh.atm.util.observeNonNull
import com.sudansh.atm.util.snack
import com.sudansh.atm.util.visible
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.ext.android.inject


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mMap: GoogleMap
    private var mClusterManager: ClusterManager<Atm>? = null
    private val vm: MapsViewModel  by inject()
    private val rvAdapter by lazy { AtmAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        getSystemService(Context.LOCATION_SERVICE)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        mMap = googleMap
        mClusterManager = ClusterManager(this, mMap)
        mMap.setOnCameraIdleListener(mClusterManager)

        //recycler view adapter
        rv_atm.adapter = rvAdapter

        autoSearch.setOnFocusChangeListener { v, hasFocus ->
        }

        vm.listAtm.observeNonNull(this) {
            updateAtms(it)
        }
        vm.isLoading.observeNonNull(this) {
            progressBar.visible(it)
        }

        //Check location permission
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        } else {
            mMap.isMyLocationEnabled = true
            if (mGoogleApiClient == null) {
                buildGoogleApiClient()
            }
        }
    }

    private lateinit var searchAdapter: ArrayAdapter<String>

    private fun initSearch(map: List<String>) {
        searchAdapter = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, map)
        autoSearch.setOnItemClickListener { adapterView: AdapterView<*>, _: View, i: Int, _: Long ->
            autoSearch.hideKeyboard()
            val atmName: String = adapterView.getItemAtPosition(i) as String
            AlertDialog.Builder(this@MapsActivity).setMessage("Welcome to $atmName").create().show()
        }
        autoSearch.setAdapter(searchAdapter)
    }

    /**
     * Update the recyclerview with the atm list
     */
    private fun updateAtms(resource: Resource<List<Atm>>) {
        when (resource.status) {
            SUCCESS -> {
                buildMarkers(resource.data.orEmpty())
                rvAdapter.updateItems(resource.data.orEmpty())
                initSearch(resource.data.orEmpty().map { it.name })
                vm.isLoading.value = false
            }
            LOADING -> vm.isLoading.value = true
            ERROR -> {
                vm.isLoading.value = false
                mainContainer.snack(resource.message.orEmpty())
            }
        }
    }

    /**
     * Create a latlng boundary to center the map based on all the pins
     */
    private fun buildMarkers(list: List<Atm>) {
        val bounds = LatLngBounds.Builder()
        list.forEach { bounds.include(it.position) }
        mClusterManager?.apply {
            clearItems()
            addItems(list)
        }.run {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 30))
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleApiClient == null) {
                buildGoogleApiClient()
            }
            mMap.isMyLocationEnabled = true
        }
    }

    @Synchronized
    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient?.connect()
    }

    /**
     * Send myLocation to [rvAdapter] if location is successfully fetched
     */
    override fun onLocationChanged(location: Location?) {
        location?.let {
            val latLng = LatLng(location.latitude, location.longitude)
            rvAdapter.setCurrentLocation(latLng)
            //stop location updates
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
            }
        }
    }

    override fun onConnected(bundle: Bundle?) {
        val mLocationRequest = LocationRequest().apply {
            interval = 3000
            fastestInterval = 3000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        if (ActivityCompat.checkSelfPermission(this@MapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }

    override fun onConnectionSuspended(p0: Int) {}

    companion object {
        const val REQUEST_LOCATION = 1000
    }
}
