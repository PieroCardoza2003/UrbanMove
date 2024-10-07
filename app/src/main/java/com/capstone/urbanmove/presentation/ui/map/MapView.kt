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
import androidx.fragment.app.Fragment
import com.capstone.urbanmove.R
import com.capstone.urbanmove.presentation.ui.map.Funcion.bottom.BottomSheet_Funcion
import com.capstone.urbanmove.presentation.ui.map.Funcion.fragment_1
import com.capstone.urbanmove.presentation.ui.map.Funcion.fragment_2
import com.capstone.urbanmove.presentation.ui.map.Funcion.fragment_3
import com.capstone.urbanmove.presentation.ui.map.Funcion.fragment_4
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog

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

        // Mostrar el BottomSheet al iniciar la actividad
        val bottomSheet = BottomSheet_Funcion()
        bottomSheet.show(supportFragmentManager, BottomSheet_Funcion.TAG)

        // Detectar cambios en el BackStack
        supportFragmentManager.addOnBackStackChangedListener {
            handleBackStackChange()
        }
    }

    private fun initializeViews() {
        // Aquí inicializar cualquier vista necesaria
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

        profileImage.setOnClickListener {
            // Manejar clic en la imagen de perfil si es necesario
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
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
    }

    // Mostrar fragment_1 con botones visibles
    private fun showFragment1() {
        setButtonsVisibility(View.VISIBLE)
        replaceFragment(fragment_1())
    }

    // Mostrar fragment_2 y ocultar botones
    fun showFragment2() {
        setButtonsVisibility(View.GONE)
        replaceFragment(fragment_2())
    }

    // Mostrar fragment_3 con botones visibles
    fun showFragment3() {
        setButtonsVisibility(View.VISIBLE)
        replaceFragment(fragment_3())
    }

    // Mostrar fragment_4 con botones visibles
    fun showFragment4() {
        setButtonsVisibility(View.VISIBLE)
        replaceFragment(fragment_4())
    }

    // Reemplazar el fragment actual por otro
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    // Manejar el cambio de fragmentos en el BackStack
    private fun handleBackStackChange() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment is fragment_2) {
            setButtonsVisibility(View.GONE)  // Ocultar botones en fragment_2
        } else {
            setButtonsVisibility(View.VISIBLE)  // Mostrar botones en otros fragmentos
        }
    }

    // Controlar la visibilidad de los botones
    private fun setButtonsVisibility(visibility: Int) {
        findViewById<ImageButton>(R.id.menu_button).visibility = visibility
        findViewById<ImageButton>(R.id.zoom_in_button).visibility = visibility
        findViewById<ImageButton>(R.id.zoom_out_button).visibility = visibility
        findViewById<ImageButton>(R.id.my_location_button).visibility = visibility
        findViewById<ImageView>(R.id.profile_image).visibility = visibility
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

    private fun enableLocation() {
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
                            .radius(100.0)
                            .strokeColor(Color.RED)
                            .fillColor(0x22FF0000)
                            .strokeWidth(4f)
                    )

                    // Mover la cámara a la ubicación del usuario
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f))
                }
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun setupMapUI() {
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleLocationButton()
            } else {
                Toast.makeText(this, "Se requiere permiso de ubicación", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
