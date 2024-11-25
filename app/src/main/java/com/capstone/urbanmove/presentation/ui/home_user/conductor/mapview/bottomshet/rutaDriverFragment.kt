package com.capstone.urbanmove.presentation.ui.home_user.conductor.mapview.bottomshet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.FragmentRegisterDriverF1Binding
import com.capstone.urbanmove.databinding.FragmentRutaDriverBinding
import com.capstone.urbanmove.databinding.FragmentRutasBinding
import com.capstone.urbanmove.presentation.ui.home_user.conductor.DriverViewModel


class rutaDriverFragment : Fragment() {

    private val viewModel: DriverViewModel by activityViewModels() //viewModel compartido
    private var _binding: FragmentRutaDriverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRutaDriverBinding.inflate(inflater, container, false)

        viewModel.driver_data.observe(viewLifecycleOwner){
            binding.textviewMensajeBienvenida.text = "Hola ${it.nombres}, ¿Qué ruta realizarás?"
            if (it.tipo_conductor == "PRIVADO"){
                viewModel.fetchRutasPrivado()
            }
        }

        binding.buttonIniciarRuta.setOnClickListener {
            viewModel.connect()
        }

        binding.btnIda.setOnClickListener{
            binding.btnIda.setBackgroundResource(R.drawable.bg_btn_border_selected)
            binding.btnVuelta.setBackgroundResource(R.drawable.bg_btn_border_unselected)
            viewModel.trayectoria = "Ida"
        }

        binding.btnVuelta.setOnClickListener{
            binding.btnVuelta.setBackgroundResource(R.drawable.bg_btn_border_selected)
            binding.btnIda.setBackgroundResource(R.drawable.bg_btn_border_unselected)
            viewModel.trayectoria = "Vuelta"
        }

        viewModel.listaRutas.observe(viewLifecycleOwner){ result ->
            Log.d("prints", "$result")
            binding.edittextUnidad.setText("1")
            binding.edittextLetraRuta.setText("AC")
        }


        binding.buttonAlcance.setOnClickListener {
            findNavController().navigate(R.id.action_to_alcance_driver)
        }

        return binding.root
    }


}