package org.wit.sites.activities.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_site_maps.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.sites.R
import org.wit.sites.helpers.readImageFromPath
import org.wit.sites.main.MainApp
import org.wit.sites.models.SiteModel

class SiteMapsActivity : AppCompatActivity(), AnkoLogger, GoogleMap.OnMarkerClickListener {

    lateinit var app: MainApp
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_site_maps)
        setSupportActionBar(toolbar)
        mapView.onCreate(savedInstanceState);
        app = application as MainApp

        mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    fun configureMap() {
        map.uiSettings.setZoomControlsEnabled(true)
        app.sites.findAll().forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
        }
        map.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        doAsync {
            val site = app.sites.findById(tag)
            uiThread {
                if (site != null) {
                    currentTitle.text = site.title
                    currentDescription.text = site.description
                    Glide.with(this@SiteMapsActivity).load(site.image1).into(currentImage)
                }
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
