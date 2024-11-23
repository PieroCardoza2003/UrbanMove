package com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.capstone.urbanmove.databinding.FragmentRegisterDriverPrivadoBinding
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.BottomSheetColor
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.BottomSheetMarca
import com.capstone.urbanmove.presentation.ui.home_user.conductor.registerDriver.privado.vehicle.BottomSheetModelo

class RegisterDriverPrivadoFragment : Fragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentRegisterDriverPrivadoBinding? = null
    private val binding get() = _binding!!

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

            viewModel.v_placa = numero_placa

            Log.d("Prints", "\nDatos personales:" +
                    "\nNombres: ${viewModel.c_nombres}" +
                    "\nApellidos: ${viewModel.c_apellidos}" +
                    "\nfecha_nacimiento: ${viewModel.c_fecha_nacimiento}" +
                    "\nfoto_perfil: ${viewModel.c_foto_perfil}" +
                    "\n\nDatos conductor:" +
                    "\nnumero_licencia: ${viewModel.c_numero_licencia}" +
                    "\nfecha_vencimiento: ${viewModel.c_fecha_vencimiento}" +
                    "\nfoto_frontal: ${viewModel.c_licencia_frontal}" +
                    "\nfoto_reverso: ${viewModel.c_licencia_reverso}" +
                    "\n\nDatos Vehiculo:" +
                    "\nplaca: ${viewModel.v_placa}" +
                    "\nmarca: ${viewModel.v_marca.value}" +
                    "\nmodelo: ${viewModel.v_modelo.value}" +
                    "\ncolor: ${viewModel.v_color.value}")
        }

        return binding.root
    }

}