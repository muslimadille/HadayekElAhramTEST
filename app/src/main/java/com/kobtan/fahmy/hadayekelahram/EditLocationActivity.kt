package com.kobtan.fahmy.hadayekelahram

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import android.location.*
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import android.os.Looper

import android.widget.TextView

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.kobtan.fahmy.hadayekelahram.R.*
import com.kobtan.fahmy.hadayekelahram.R.id

import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.IOException
import java.util.*


class EditLocationActivity : AppCompatActivity(),OnMapReadyCallback , LocationListener {
    lateinit  var locationManager: LocationManager
    private lateinit var mMap: GoogleMap
    var fusedLocationProviderClient: FusedLocationProviderClient?=null
    var currentLocation: Location?=null
    var currentMrker:Marker?=null
    var lat="3.000"
    var lng="2.000"
    var saveBt:TextView?=null

    private lateinit var locationCallback2: LocationCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_edit_location)
        saveBt=findViewById(id.save_location_btn)
        saveBt!!.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("LAT_LNG", "${lat}#${lng}")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        locationCallback2 = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                if(p0.lastLocation!=null&&currentLocation==null){
                    currentLocation=p0.lastLocation
                    val mapFragment = supportFragmentManager
                        .findFragmentById(id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this@EditLocationActivity)
                }
            }
        }
        fetchLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMyLocationButtonEnabled = true
        var lat_long:LatLng
        this.currentLocation.let {
            lat_long= LatLng(it?.latitude!!,it.longitude)
        }
        drawMarker(lat_long)
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDragStart(p0: Marker) {
            }

            override fun onMarkerDrag(p0: Marker) {
            }

            override fun onMarkerDragEnd(p0: Marker) {

                if(currentMrker!=null){
                    currentMrker?.remove()
                    var newLatLng= LatLng(p0.position.latitude,p0.position.longitude)
                    drawMarker(newLatLng)


                }
            }
        })

    }
    private fun drawMarker(lat_long: LatLng){
        val markerOption= MarkerOptions().position(lat_long).draggable(true)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(lat_long))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat_long,15f))
        currentMrker=mMap.addMarker(markerOption)
        currentMrker?.showInfoWindow()
        lat=lat_long.latitude.toString()
        lng=lat_long.longitude.toString()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000->{if(grantResults.size!=0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }}
        }
    }
    fun fetchLocation(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1000)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),1000)

            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100L,10f,this@EditLocationActivity)
        } else {

            startLocationUpdates()

        }

    }

    var locationRequest: LocationRequest = LocationRequest()
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(locationRequest,
            locationCallback2,
            Looper.getMainLooper())
    }
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient!!.removeLocationUpdates(locationCallback2)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onLocationChanged(p0: Location) {

        if (null != p0&&currentLocation==null) {
            this.currentLocation=p0
            val mapFragment = supportFragmentManager
                .findFragmentById(id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

        }

    }

}