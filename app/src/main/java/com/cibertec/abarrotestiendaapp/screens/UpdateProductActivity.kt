package com.cibertec.abarrotestiendaapp.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cibertec.abarrotestiendaapp.databinding.ActivityRegisterProductBinding
import com.cibertec.abarrotestiendaapp.databinding.ActivityUpdateProductBinding
import com.cibertec.abarrotestiendaapp.entity.Product
import com.cibertec.abarrotestiendaapp.showAlertDialog
import com.cibertec.abarrotestiendaapp.utils.AppAbarrotesConfig
import com.cibertec.abarrotestiendaapp.utils.AppAbarrotesConfig.Companion.databaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class UpdateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()

        // Ajustar padding para evitar que el contenido se solape con los insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val productId = intent.getStringExtra("productId") ?: return

        gerProductById(productId)

        binding.btnGrabar.setOnClickListener {
            updateProduct(productId)
        }

    }

    private fun updateProduct(productId: String) {
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
            idFirebase = productId,
            nombre = nombrePlato,
            descripcion = descripcion,
            precio = precio.toDouble(),
            categoria = categoria,
            imagenUrl = imgUrl)


        databaseReference.child("products").child(productId).setValue(product)
            .addOnSuccessListener {
                Toast.makeText(this@UpdateProductActivity, "Producto actualizado correctamente.", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                showAlertDialog(this@UpdateProductActivity, "Error al actualizar producto: ${e.message}")
            }
    }


    private fun gerProductById(productId: String) {
        databaseReference.child("products").child(productId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val product = snapshot.getValue(Product::class.java)
                        binding.edtNameProduct.setText(product?.nombre)
                        binding.edtDescripcion.setText(product?.descripcion)
                        binding.edtPrecio.setText(product?.precio.toString())
                        binding.edtImageUrl.setText(product?.imagenUrl)
                        binding.spnCategoria.setText(product?.categoria, false)
                    } else {
                        showAlertDialog(this@UpdateProductActivity, "No se encontró el producto con ID: $productId")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    showAlertDialog(this@UpdateProductActivity, error.message)
                }
            })
    }
}
