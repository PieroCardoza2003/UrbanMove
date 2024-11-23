package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentRegisterDriverF3Binding
import com.capstone.urbanmove.presentation.ui.common.PopupPermission
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterDriverF3Fragment : Fragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentRegisterDriverF3Binding? = null
    private val binding get() = _binding!!

    private var currentImage: Int = 0
    private lateinit var popupPermission: PopupPermission

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDriverF3Binding.inflate(inflater, container, false)
        popupPermission = PopupPermission(requireActivity())


        binding.edittextFechavencimiento.setOnClickListener{
            showDatePickerDialog(binding.edittextFechavencimiento)
        }

        binding.imageviewLicenciaReverso.setOnClickListener {
            currentImage = 2
            checkStoragePermission()
        }

        binding.imageviewLicenciaFrontral.setOnClickListener {
            currentImage = 1
            checkStoragePermission()
        }

        binding.btnSiguiente.setOnClickListener{
            val licencia_frontal = viewModel.c_licencia_frontal
            val licencia_reverso = viewModel.c_licencia_reverso
            val numero_licencia = binding.edittextNroLicencia.text.toString()
            val fecha_vencimiento = binding.edittextFechavencimiento.text.toString()

            if (licencia_frontal.isBlank()) {
                binding.imageviewLicenciaFrontral.setBackgroundResource(R.drawable.bg_btn_border_selected)
                return@setOnClickListener
            }
            if (licencia_reverso.isBlank()) {
                binding.imageviewLicenciaReverso.setBackgroundResource(R.drawable.bg_btn_border_selected)
                return@setOnClickListener
            }
            if (numero_licencia.isBlank()){
                binding.layoutNroLicencia.error = "Campo obligatorio"
                return@setOnClickListener
            }
            if (fecha_vencimiento.isBlank()){
                binding.layoutFechavencimiento.error = "Campo obligatorio"
                return@setOnClickListener
            }

            viewModel.c_numero_licencia = numero_licencia
            viewModel.c_fecha_vencimiento = fecha_vencimiento


            if (viewModel.c_tipo_conductor == "EMPRESA")
                findNavController().navigate(R.id.action_to_register_empresa)
            else if (viewModel.c_tipo_conductor == "PRIVADO")
                findNavController().navigate(R.id.action_to_register_privado)
        }

        return binding.root
    }

    private fun checkStoragePermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            requestPermissionLauncher.launch(permission)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
                popupPermission.showPermissionDeniedPopup(popupPermission.permissionType(permission))
            }
        }

    private fun openGallery() {
        val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(pickImageIntent)
    }

    private var imagePickerLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                val sd = getRealPathFromURI(imageUri!!)

                if (currentImage == 1) { // licencia frontal
                    binding.imageviewLicenciaFrontral.setImageURI(imageUri)
                    viewModel.c_licencia_frontal = sd!!
                    binding.imageviewLicenciaFrontral.setBackgroundResource(R.drawable.bg_btn_border_unselected)
                    Log.d("prints", "frontal: \nURI> $imageUri \nReal> ${viewModel.c_licencia_frontal}")
                } else if (currentImage == 2) { // licencia trasera
                    binding.imageviewLicenciaReverso.setImageURI(imageUri)
                    viewModel.c_licencia_reverso = sd!!
                    binding.imageviewLicenciaReverso.setBackgroundResource(R.drawable.bg_btn_border_unselected)
                    Log.d("prints", "trasera: \nURI> $imageUri \nReal> ${viewModel.c_licencia_reverso}")
                }
            }
        }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        var filePath: String? = null
        val cursor = requireContext().contentResolver.query(contentUri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
        }
        return filePath
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}