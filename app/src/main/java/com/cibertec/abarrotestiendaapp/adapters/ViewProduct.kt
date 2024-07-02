package com.cibertec.abarrotestiendaapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.abarrotestiendaapp.R

class ViewProduct(item: View) : RecyclerView.ViewHolder(item) {

    var tvNombre: TextView
    var tvDescripcion: TextView
    var tvPrecio: TextView
    var tvCategoria: TextView
    var ivProduct: ImageView
    var btnDelete: ImageView
    var btnUpdate: ImageView

    init {
        tvNombre = item.findViewById(R.id.tvNombre)
        tvDescripcion = item.findViewById(R.id.tvDescripcion)
        tvPrecio = item.findViewById(R.id.tvPrecio)
        tvCategoria = item.findViewById(R.id.tvTipoPlato)
        ivProduct = item.findViewById(R.id.imgFoto)
        btnDelete = item.findViewById(R.id.btnDelete)
        btnUpdate = item.findViewById(R.id.btnUpdate)
    }
}