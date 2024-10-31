package com.capstone.urbanmove.Tes_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.capstone.urbanmove.R
import com.capstone.urbanmove.databinding.ActivityInfoVehiculoBinding

class InfoVehiculoActivity : AppCompatActivity(),
    MarcaFragment.OnItemSelectedListener,
    ModeloFragment.OnItemSelectedListener,
    ColorFragment.OnItemSelectedListener{

    private lateinit var binding: ActivityInfoVehiculoBinding
    private var selectedIdMarca: Int? = null// Para guardar el id_marca seleccionado
    private var selectedIdModelo: Int? = null // Para guardar el id de modelo seleccionado
    private var selectedIdColor: Int? = null // Para guardar el id de color seleccionado
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoVehiculoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMarcaVehiculo.setOnClickListener {
            val marcaFragment = MarcaFragment()
            marcaFragment.show(supportFragmentManager, marcaFragment.tag)
        }

        binding.tvModeloVehiculo.setOnClickListener {
            val modeloFragment = ModeloFragment.newInstance(selectedIdMarca)
            modeloFragment.show(supportFragmentManager, modeloFragment.tag)
        }

        binding.tvColorVehiculo.setOnClickListener {
            val colorFragment = ColorFragment()
            colorFragment.show(supportFragmentManager, colorFragment.tag)
        }

        binding.btnAceptarInfoVehiculo.setOnClickListener {
            verificarCamposYMostrarConfirmacion()
        }

    }
    private fun verificarCamposYMostrarConfirmacion() {
        val placa = binding.etNumeroPlaca.text.toString()
        val marca = binding.tvMarcaVehiculo.text.toString()
        val modelo = binding.tvModeloVehiculo.text.toString()
        val color = binding.tvColorVehiculo.text.toString()

        if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || color.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Debe llenar todos los campos antes de continuar.")
        } else {
            mostrarConfirmacionDialog(placa, marca, modelo, color)
        }
    }

    private fun mostrarConfirmacionDialog(placa: String, marca: String, modelo: String, color: String) {
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro de que los datos ingresados son correctos?")
            .setPositiveButton("Sí") { _, _ ->
                mostrarAlertaConDatos(placa, marca, modelo, color)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun mostrarAlertaConDatos(placa: String, marca: String, modelo: String, color: String) {
        val mensaje = """
            Usted ingresó el vehículo con:
            - Número de placa: $placa
            - Marca: $marca (ID: $selectedIdMarca)
            - Modelo: $modelo (ID: $selectedIdModelo)
            - Color: $color (ID: $selectedIdColor)
        """.trimIndent()

        mostrarAlerta("Vehículo Ingresado", mensaje)
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    override fun onItemSelected(item: Marca) {
        selectedIdMarca = item.id_marca
        findViewById<TextView>(R.id.tvMarcaVehiculo).text = item.marca

        selectedIdModelo = null
        findViewById<TextView>(R.id.tvModeloVehiculo).text = ""
    }
    override fun onItemSelected(item: Modelo) {
        selectedIdModelo = item.id_modelo
        findViewById<TextView>(R.id.tvModeloVehiculo).text = item.modelo
    }
    override fun onItemSelected(item: VehicleColor) {
        selectedIdColor = item.id_color
        findViewById<TextView>(R.id.tvColorVehiculo).text = item.color
    }
}