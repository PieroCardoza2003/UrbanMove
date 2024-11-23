package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentRegisterDriverF2Binding
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.Manifest
import android.os.Build
import com.capstone.urbanmove.presentation.ui.common.PopupPermission

class RegisterDriverF2Fragment : Fragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentRegisterDriverF2Binding? = null
    private val binding get() = _binding!!
    private lateinit var popupPermission: PopupPermission

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDriverF2Binding.inflate(inflater, container, false)
        popupPermission = PopupPermission(requireActivity())

        check_data()

        binding.imageviewFotoPerfil.setOnClickListener {
            checkStoragePermission()
        }

        binding.edittextFechanacimiento.setOnClickListener{
            showDatePickerDialog(binding.edittextFechanacimiento)
        }

        binding.btnSiguiente.setOnClickListener{
            val nombre = binding.edittextNombre.text.toString()
            val apellidos = if (binding.edittextApellidos.text.isNullOrEmpty()) null else binding.edittextApellidos.text.toString()
            val fechanacimiento = if(binding.edittextFechanacimiento.text.isNullOrEmpty()) null else binding.edittextFechanacimiento.text.toString()
            val foto_perfil = viewModel.c_foto_perfil

            if (foto_perfil.isBlank()) {
                binding.imageviewFotoPerfil.setBackgroundResource(R.drawable.bg_btn_border_selected)
                return@setOnClickListener
            }

            if (nombre.isBlank()){
                binding.layoutNombre.error = "Campo obligatorio"
                return@setOnClickListener
            }
            if (apellidos.isNullOrBlank()) {
                binding.layoutApellidos.error = "Campo obligatorio"
                return@setOnClickListener
            }
            if (fechanacimiento.isNullOrBlank()) {
                binding.layoutFechanacimiento.error = "Campo obligatorio"
                return@setOnClickListener
            }

            viewModel.c_nombres = nombre
            viewModel.c_apellidos = apellidos
            viewModel.c_fecha_nacimiento = fechanacimiento

            findNavController().navigate(R.id.action_to_documentos_personales)
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

                binding.imageviewFotoPerfil.setImageURI(imageUri)
                viewModel.c_foto_perfil =  sd!!
                viewModel.c_foto_perfil_type = "GALLERY"
                binding.imageviewFotoPerfil.setBackgroundResource(R.drawable.bg_btn_border_unselected)
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

    private fun check_data(){
        val data = viewModel.accountDriver ?: return

        binding.edittextNombre.setText(data.nombres)
        binding.edittextApellidos.setText(data.apellidos)
        binding.edittextFechanacimiento.setText(data.fecha_nacimiento)

        if (data.foto_perfil != null) {
            Glide.with(requireContext()).load(data.foto_perfil).into(binding.imageviewFotoPerfil)
            viewModel.c_foto_perfil = data.foto_perfil
            viewModel.c_foto_perfil_type = "URL"
        }
    }
}