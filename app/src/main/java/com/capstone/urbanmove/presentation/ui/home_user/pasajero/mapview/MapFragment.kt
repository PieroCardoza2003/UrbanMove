package com.capstone.urbanmove.presentation.ui.home_user.pasajero.mapview

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentMapBinding
import com.capstone.urbanmove.presentation.ui.common.PopupPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MapFragment : Fragment(), OnMapReadyCallback {


    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private lateinit var map: GoogleMap

    private lateinit var popupPermission: PopupPermission

    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        popupPermission = PopupPermission(requireActivity())


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
        this.map.setOnCameraMoveStartedListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(3000)
                .build()

            // Inicializa el LocationCallback
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.locations.forEach { location ->
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        Log.d("prints", "actual: $currentLocation")

                    }
                }
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



}