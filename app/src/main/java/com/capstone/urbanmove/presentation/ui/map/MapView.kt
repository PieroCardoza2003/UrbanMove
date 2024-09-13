package com.capstone.urbanmove.presentation.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstone.urbanmove.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapView : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var searchingCard: RelativeLayout
    private lateinit var redBar: View

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)
        initializeViews()
        createFragment()
        setupButtons()
    }

    private fun initializeViews() {
        searchingCard = findViewById(R.id.searching_card)
        redBar = findViewById(R.id.red_bar)
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupButtons() {
        val menuButton: ImageButton = findViewById(R.id.menu_button)
        val zoomInButton: ImageButton = findViewById(R.id.zoom_in_button)
        val zoomOutButton: ImageButton = findViewById(R.id.zoom_out_button)
        val myLocationButton: ImageButton = findViewById(R.id.my_location_button)
        val closeButton: ImageButton = findViewById(R.id.close_button)
        val profileImage: ImageView = findViewById(R.id.profile_image)

        menuButton.setOnClickListener {
            Toast.makeText(this, "Menú presionado", Toast.LENGTH_SHORT).show()
        }

        zoomInButton.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }

        zoomOutButton.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }

        myLocationButton.setOnClickListener {
            handleLocationButton()
        }

        closeButton.setOnClickListener {
            hideSearchingCard()
        }

        profileImage.setOnClickListener {
            // Handle profile image click if needed
        }
    }

    private fun handleLocationButton() {
        if (isLocationPermissionGranted()) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f))
                } else {
                    Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    private fun showSearchingCard() {
        redBar.visibility = RelativeLayout.VISIBLE
        searchingCard.visibility = RelativeLayout.VISIBLE
    }

    private fun hideSearchingCard() {
        searchingCard.visibility = RelativeLayout.GONE
        redBar.visibility = RelativeLayout.GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableLocation()
        setupMapUI()
    }

    private fun setupMapUI() {
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
    }

    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = false // Desactiva el marcador azul predeterminado

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)

                    // Agregar marcador personalizado
                    map.addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .title("Mi ubicación")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )

                    // Dibujar un círculo alrededor del marcador
                    map.addCircle(
                        CircleOptions()
                            .center(userLocation)
                            .radius(100.0) // Radio en metros
                            .strokeColor(Color.RED) // Color del borde del círculo
                            .fillColor(0x22FF0000) // Color de relleno (rojo con transparencia)
                            .strokeWidth(4f) // Ancho del borde del círculo
                    )

                    // Mover la cámara a la ubicación del usuario
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f))
                }
            }
        } else {
            requestLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableLocation()
                } else {
                    Toast.makeText(
                        this,
                        "Para activar la localización ve a ajustes y acepta los permisos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if (!isLocationPermissionGranted()) {
            map.isMyLocationEnabled = false
            Toast.makeText(
                this,
                "Para activar la localización ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
