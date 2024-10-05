package com.capstone.urbanmove


import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest
import android.animation.ValueAnimator
import android.content.BroadcastReceiver
import android.os.Looper
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.location.Priority
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import android.graphics.Color
import android.util.Log
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.model.Marker

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var userInteracted = false
    private var userCircle: Circle? = null // Círculo del usuario
    private var userMarker: Marker? = null
    private val DEFAULT_LOCATION = LatLng(-8.109052, -79.021533)  // Trujillo
    private val DEFAULT_ZOOM = 11f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el launcher para los permisos de ubicación
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // El permiso fue otorgado
                getLocationUpdates()
            } else {
                // El permiso fue denegado
                Toast.makeText(requireContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
                showDefaultLocation()

            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Solicitar permisos
        requestLocationPermissions()

        view.findViewById<Button>(R.id.btnEnableLocation).setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }
    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        if (!isLocationEnabled()) {
            showDefaultLocation()
        }

        getLocationUpdates()

        googleMap.setOnCameraMoveStartedListener { reason ->
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                userInteracted = true
            }
        }

    }

    private fun getLocationUpdates() {
        // Configura las solicitudes de ubicación (Aquí manejo los segundos por actualización, en este caso en un
        // rango de 2 y 3 segundos)
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000)
            .setMaxUpdateDelayMillis(3000)
            .build()

        // Inicializa el LocationCallback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    // Actualiza la ubicación en el mapa
                    for (location in locationResult.locations) {
                        updateLocationOnMap(location)
                    }
                } else {
                    // Si no se encuentra ubicación, muestra la predeterminada
                    showDefaultLocation()
                }
            }

            override fun onLocationAvailability(p0: LocationAvailability) {
                if (!p0.isLocationAvailable) {
                    showDefaultLocation()
                    Log.d("MapFragment","Ubicacion NO obtenida")
                    view?.findViewById<LinearLayout>(R.id.locationDisabledLayout)?.visibility = View.VISIBLE
                }else{
                    Log.d("MapFragment","Ubicacion obtenida")
                    view?.findViewById<LinearLayout>(R.id.locationDisabledLayout)?.visibility = View.GONE
                }
            }
        }

        // Verificando si el permiso ha sido concedido
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Pide actualizaciones de la ubicación
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } else {
            // Maneja la falta de permisos
            requestLocationPermissions()
            showDefaultLocation()
        }
    }

    private fun updateLocationOnMap(location: Location) {
        val currentLatLng = LatLng(location.latitude, location.longitude)

        if (!userInteracted) {

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
        }

        //Marcador
        if (userMarker != null) {
            animateMarker(userMarker!!, currentLatLng)
        } else {
            userMarker = googleMap.addMarker(MarkerOptions().position(currentLatLng).title("Estás aquí"))
        }

        // Círculo
        if (userCircle != null) {
            userCircle?.center = currentLatLng
        } else {
            // Crear un círculo con un radio de 500 metros
            userCircle = googleMap.addCircle(
                CircleOptions()
                    .center(currentLatLng)
                    .radius(500.0) // 500 metros
                    .strokeColor(Color.RED) // Color del borde
                    .fillColor(Color.argb(70, 150, 50, 50))
                    .strokeWidth(5f)
            )
        }

    }
    private fun animateMarker(marker: Marker, toPosition: LatLng) {
        val startLatLng = marker.position
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 1000 // Duración de 1 segundo para la animación
        valueAnimator.interpolator = LinearInterpolator()

        valueAnimator.addUpdateListener { animation ->
            val fraction = animation.animatedFraction
            val lat = fraction * toPosition.latitude + (1 - fraction) * startLatLng.latitude
            val lng = fraction * toPosition.longitude + (1 - fraction) * startLatLng.longitude
            val newLatLng = LatLng(lat, lng)
            marker.position = newLatLng
        }

        valueAnimator.start()
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    private fun showDefaultLocation() {
        // Mover el mapa a las coordenadas predeterminadas con el zoom especificado (las que se declararon arriba, osea Trujilo)
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(DEFAULT_LOCATION).title("Ubicación predeterminada"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM))

    }
    private fun updateLocationUI() {
        if (isLocationEnabled()) {
            view?.findViewById<LinearLayout>(R.id.locationDisabledLayout)?.visibility = View.GONE
            Log.d("UI","Ubicacion Obtenida")
        } else {
            view?.findViewById<LinearLayout>(R.id.locationDisabledLayout)?.visibility = View.VISIBLE
            Log.d("UI","Ubicacion NO Obtenida")
        }
    }
    override fun onPause() {
        super.onPause()
        // Resetea la interacción del usuario
        userInteracted = false
        updateLocationUI()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        updateLocationUI()
        getLocationUpdates()
    }

    private fun requestLocationPermissions() {
        requestPermissionLauncher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}