package com.cibertec.abarrotestiendaapp.screens

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cibertec.abarrotestiendaapp.databinding.ActivityRegisterProductBinding
import com.cibertec.abarrotestiendaapp.entity.Product
import com.cibertec.abarrotestiendaapp.showAlertDialog
import com.cibertec.abarrotestiendaapp.utils.AppAbarrotesConfig.Companion.databaseReference

class RegisterProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()

        // Ajustar padding para evitar que el contenido se solape con los insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar listener para el botón de guardar
        binding.btnGrabar.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val nombrePlato = binding.edtNameProduct.text.toString().trim()
        val descripcion = binding.edtDescripcion.text.toString().trim()
        val precio = binding.edtPrecio.text.toString().trim()
        val imgUrl = binding.edtImageUrl.text.toString().trim()
        val categoria = binding.spnCategoria.text.toString()

        if (categoria.isEmpty() || nombrePlato.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || imgUrl.isEmpty()) {
            showAlertDialog(this, "Por favor completa todos los campos.")
            return
        }


        val product = Product(
            nombre = nombrePlato,
            descripcion = descripcion,
            precio = precio.toDouble(),
            categoria = categoria,
            imagenUrl = imgUrl)

            val platoId = databaseReference.child("products").push().key ?: return
            product.idFirebase = platoId

            databaseReference.child("products").child(platoId).setValue(product)
                .addOnSuccessListener {
                    showAlertDialog(this@RegisterProductsActivity, "Producto registrado correctamente.")
                    clearFields()
                }
                .addOnFailureListener { e ->
                    showAlertDialog(this@RegisterProductsActivity, "Error al registrar producto: ${e.message}")
                }
    }

    private fun clearFields() {
        binding.edtNameProduct.setText("")
        binding.edtDescripcion.setText("")
        binding.edtPrecio.setText("")
        binding.spnCategoria.setText("", false)
        binding.edtImageUrl.setText("")
    }
}
