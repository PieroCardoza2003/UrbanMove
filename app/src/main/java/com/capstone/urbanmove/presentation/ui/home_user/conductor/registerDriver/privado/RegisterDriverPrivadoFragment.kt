package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.databinding.FragmentRegisterDriverPrivadoBinding
import com.capstone.urbanmove.domain.entity.Result
import com.capstone.urbanmove.presentation.ui.common.ErrorActivity
import com.capstone.urbanmove.presentation.ui.common.LoadDialogFragment
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverActivity
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.BottomSheetColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.BottomSheetMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.BottomSheetModelo

class RegisterDriverPrivadoFragment : Fragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentRegisterDriverPrivadoBinding? = null
    private val binding get() = _binding!!
    private var loadDialogFragment: LoadDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDriverPrivadoBinding.inflate(inflater, container, false)

        viewModel.v_marca.observe(viewLifecycleOwner){ marca ->
            binding.edittextMarcaVehiculo.setText(marca.marca)
        }

        viewModel.v_modelo.observe(viewLifecycleOwner){ modelo ->
            binding.edittextModeloVehiculo.setText(modelo.modelo)
        }

        viewModel.v_color.observe(viewLifecycleOwner){ color ->
            binding.edittextColorVehiculo.setText(color.color)
        }

        binding.edittextMarcaVehiculo.setOnClickListener{
            val marcaFragment = BottomSheetMarca()
            marcaFragment.show(parentFragmentManager, marcaFragment.tag)
            binding.edittextModeloVehiculo.setText("")
        }

        binding.edittextModeloVehiculo.setOnClickListener{
            val marca = binding.edittextMarcaVehiculo.text.toString()

            if (marca.isBlank()){
                binding.layoutMarcaVehiculo.error = "Campo obligatorio"
                return@setOnClickListener
            }

            val modeloFragment = BottomSheetModelo()
            modeloFragment.show(parentFragmentManager, modeloFragment.tag)
        }

        binding.edittextColorVehiculo.setOnClickListener{
            val modelo = binding.edittextModeloVehiculo.text.toString()

            if (modelo.isBlank()){
                binding.layoutModeloVehiculo.error = "Campo obligatorio"
                return@setOnClickListener
            }

            val colorFragment = BottomSheetColor()
            colorFragment.show(parentFragmentManager, colorFragment.tag)
        }

        binding.btnAceptar.setOnClickListener {
            val numero_placa = binding.edittextNumeroPlaca.text.toString()
            val marca_vehiculo = binding.edittextMarcaVehiculo.text.toString()
            val modelo_vehiculo = binding.edittextModeloVehiculo.text.toString()
            val color_vehiculo = binding.edittextColorVehiculo.text.toString()

            if (numero_placa.isBlank()){
                binding.layoutNumeroPlaca.error = "Campo obligatorio"
                return@setOnClickListener
            }
            if (marca_vehiculo.isBlank()){
                binding.layoutMarcaVehiculo.error = "Campo obligatorio"
                return@setOnClickListener
            }
            if (modelo_vehiculo.isBlank()){
                binding.layoutModeloVehiculo.error = "Campo obligatorio"
                return@setOnClickListener
            }
            if (color_vehiculo.isBlank()){
                binding.layoutColorVehiculo.error = "Campo obligatorio"
                return@setOnClickListener
            }

            showLoadAnimation(true)
            viewModel.v_placa = numero_placa
            viewModel.registrar_conductor_privado()
        }

        viewModel.result_conductor_privado.observe(viewLifecycleOwner){ result ->
            showLoadAnimation(false)
            when(result) {
                Result.SUCCESS -> {
                    Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, DriverActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                Result.UNSUCCESS -> {
                    Toast.makeText(requireContext(), "No se ha podido registrar, vuelva a intentarlo", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, DriverActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                else -> {
                    val intent = Intent(context, ErrorActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        return binding.root
    }

    private fun showLoadAnimation(state: Boolean) {
        if (state) {
            if (loadDialogFragment == null) {
                loadDialogFragment = LoadDialogFragment("Cargando")
                loadDialogFragment?.show(childFragmentManager, "load_dialog")
            }
        } else {
            loadDialogFragment?.dismiss()
            loadDialogFragment = null
        }
    }

}