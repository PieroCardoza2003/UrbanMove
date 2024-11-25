package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentMapBinding
import com.capstone.urbanmove.presentation.ui.common.PopupPermission
import com.capstone.urbanmove.presentation.ui.home_user.pasajero.PassengerViewModel
import com.capstone.urbanmove.utils.RandomColor
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.gms.maps.model.CircleOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModelMap: MapViewModel by activityViewModels() //viewModel compartido
    private val viewmodelPassenger: PassengerViewModel by activityViewModels() //viewModel compartido

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private lateinit var map: GoogleMap

    private lateinit var popupPermission: PopupPermission

    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var currentLocationMarker: Marker? = null
    private var currentLocationCircle: Circle? = null
    private var automaticallyMoveCamera: Boolean = false
    private var socketConnected: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        popupPermission = PopupPermission(requireActivity())

        // Observar el estado de conexión
        lifecycleScope.launch {
            viewmodelPassenger.isConnected.collectLatest { isConnected ->
                socketConnected = isConnected
            }
        }


        viewModelMap.radius.observe(viewLifecycleOwner){ value ->
            currentLocationCircle?.radius = value.toDouble()
        }

        binding.butonMenuHome.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.buttonMaximizeMap.setOnClickListener{
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }

        binding.buttonMinimizeMap.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }

        binding.buttonUbicacionMap.setOnClickListener {
            requestLocationPermission()
            automaticallyMoveCamera = true
        }

        viewmodelPassenger.user.observe(viewLifecycleOwner){ usuario ->
            if (usuario.foto_perfil != null){
                Glide.with(requireContext())
                    .load(usuario.foto_perfil)
                    .into(binding.imagenPerfilPasajero)
            }
        }

        viewmodelPassenger.detectado.observe(viewLifecycleOwner){ detectado ->
            usersLocationMarkers(detectado.location)
        }

        val bottomSheet: View = binding.bottomSheetPassenger
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
            isHideable = true
            isDraggable = false
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)

        val mapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        this.map.uiSettings.isCompassEnabled = false
        this.map.uiSettings.isRotateGesturesEnabled = false
        this.map.uiSettings.isTiltGesturesEnabled = false

        this.map.setOnCameraMoveStartedListener { reason ->
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                automaticallyMoveCamera = false
            }
        }

        this.map.setOnCameraIdleListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) -> getCurrentLocation()
            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted)
                getCurrentLocation()
            else
                popupPermission.showPermissionDeniedPopup(popupPermission.permissionType(Manifest.permission.ACCESS_FINE_LOCATION))
        }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Configura las solicitudes de ubicación (Aquí manejo los segundos por actualización, en este caso en un rango de 2 y 3 segundos)
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
                .setWaitForAccurateLocation(true)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(3000)
                .build()

            // Inicializa el LocationCallback
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.locations.forEach { location ->
                        val currentLocation = LatLng(location.latitude, location.longitude)

                        updateMyLocationMarker(currentLocation)

                        if (socketConnected) {
                            viewmodelPassenger.send(currentLocation)
                        }
                    }
                }

                /*override fun onLocationAvailability(p0: LocationAvailability) {
                    Log.d("prints", "isLocationAvailable: ${p0.isLocationAvailable}")
                    if (!p0.isLocationAvailable) {
                        currentLocationMarker?.remove()
                        currentLocationCircle?.remove()
                        currentLocationMarker = null
                        currentLocationCircle = null

                        if (socketConnected)
                            viewmodelPassenger.disconnect()
                    }
                }

                 */
            }

            // Pide actualizaciones de la ubicación
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
        else
            showGPSActivationDialog()
    }

    private fun showGPSActivationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Activar GPS")
            .setMessage("Para usar esta función, necesitas activar el GPS. ¿Deseas activarlo ahora?")
            .setPositiveButton("Activar") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun updateMyLocationMarker(location: LatLng) {
        if (currentLocationMarker == null) {
            val markerOptions = MarkerOptions()
                .position(location)
                .title("Mi ubicación")
                .icon(BitmapDescriptorFactory.fromBitmap(getCustomMarker(0)))

            currentLocationMarker = map.addMarker(markerOptions)

            currentLocationCircle = map.addCircle(
                CircleOptions()
                    .center(location)
                    .radius(500.0) // 500 metros
                    .strokeColor(Color.RED) // Color del borde
                    .fillColor(0x44FF0000)
                    .strokeWidth(4f)
            )

        } else {
            currentLocationMarker?.position = location
            currentLocationCircle?.center = location
        }
        
        if (automaticallyMoveCamera)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    private var usercurrentLocationMarker: Marker? = null

    private fun usersLocationMarkers(location: LatLng) {
        if (usercurrentLocationMarker == null) {
            val markerOptions = MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.fromBitmap(getCustomMarker(1)))

            usercurrentLocationMarker = map.addMarker(markerOptions)

        } else {
            usercurrentLocationMarker?.position = location
        }
    }

    private fun getCustomMarker(option: Int): Bitmap {
        var vectorResId = 0
        var vectorColor = 0
        val vectorSize = 100

        if (option == 0) {
            vectorResId = R.drawable.icon_my_location
            vectorColor = Color.RED
        } else {
            vectorResId = R.drawable.icon_vehicle_location
            vectorColor = RandomColor.getColor()
        }

        val vectorDrawable: Drawable = ContextCompat.getDrawable(requireContext(), vectorResId)!!
        val bitmap = Bitmap.createBitmap(vectorSize, vectorSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        vectorDrawable.colorFilter = PorterDuffColorFilter(vectorColor, PorterDuff.Mode.SRC_IN)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        return bitmap
    }


}