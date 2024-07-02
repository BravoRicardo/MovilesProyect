package com.cibertec.abarrotestiendaapp.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.abarrotestiendaapp.adapters.ProductAdapter
import com.cibertec.abarrotestiendaapp.controller.ProductController
import com.cibertec.abarrotestiendaapp.databinding.ActivityListProductBinding
import com.cibertec.abarrotestiendaapp.entity.Product
import com.cibertec.abarrotestiendaapp.showAlertDialog
import com.cibertec.abarrotestiendaapp.utils.AppAbarrotesConfig
import com.cibertec.abarrotestiendaapp.utils.AppAbarrotesConfig.Companion.databaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ListProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductBinding
    private lateinit var productAdapter: ProductAdapter
    private val productController: ProductController = ProductController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        AppAbarrotesConfig.BD.writableDatabase

        productAdapter = ProductAdapter({
            deleteProductById(it)
        },{
            openUpdateProduct(it)
        })
        initProductRecyclerView()

        if(AppAbarrotesConfig.isConnectNetwork){
            loadProductsFromFirebase()
        }else{
            loadProducts()
        }

        binding.tietFilter.addTextChangedListener {
            productAdapter.filter.filter(it.toString())
        }

    }

    private fun initProductRecyclerView() {
        binding.rvProducts.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvProducts.adapter = productAdapter
    }


    private fun loadProductsFromFirebase() {
        databaseReference.child("products").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productController.clearTableProduct()
                for (row in snapshot.children) {
                    val bean = row.getValue(Product::class.java)
                    bean?.let {
                        productController.save(it)
                    }
                }
                loadProducts()
            }

            override fun onCancelled(error: DatabaseError) {
                showAlertDialog(this@ListProductActivity, error.message)
            }
        })
    }


    private fun loadProducts(){
        productAdapter.setData(productController.findAll())
    }

    private fun deleteProductById(itemId: String) {
        if(AppAbarrotesConfig.isConnectNetwork){
            databaseReference.child("products").child(itemId).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@ListProductActivity, "El producto ha sido eliminado", Toast.LENGTH_SHORT).show()
                    loadProductsFromFirebase()
                } else {
                    Log.w("Firebase", "Error al eliminar el nodo", task.exception)
                }
            }
        }else{
            showAlertDialog(this@ListProductActivity, "No cuentas con conexion a intenet")
        }
    }

    private fun openUpdateProduct(productId: String){
        val intent = Intent(this, UpdateProductActivity::class.java)
        intent.putExtra("productId", productId)
        startActivity(intent)
    }
}
