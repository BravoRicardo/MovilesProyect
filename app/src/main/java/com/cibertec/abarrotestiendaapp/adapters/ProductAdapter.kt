package com.cibertec.abarrotestiendaapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cibertec.abarrotestiendaapp.R
import com.cibertec.abarrotestiendaapp.entity.Product

class ProductAdapter(val onClickDelete: (id: String) -> Unit, val onClickUpdate: (id: String) -> Unit) : RecyclerView.Adapter<ViewProduct>(), Filterable {

    var productsList: ArrayList<Product> = ArrayList()
    var productsListFiltered: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewProduct {
        val vista: View = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewProduct(vista)
    }

    override fun getItemCount(): Int = productsListFiltered.size

    override fun onBindViewHolder(holder: ViewProduct, position: Int) {
        val product = productsListFiltered[position]
        holder.tvNombre.text = product.nombre
        holder.tvDescripcion.text = product.descripcion
        holder.tvPrecio.text = product.precio.toString()
        holder.tvCategoria.text = product.categoria
        Glide
            .with(holder.ivProduct.context)
            .load(product.imagenUrl)
            .into(holder.ivProduct)

        // Configurar el click listener para cada item
        holder.itemView.setOnClickListener {
          /*  val intent = Intent(holder.itemView.context, UpdateproductActivity::class.java).apply {
                putExtra("PLATO_ID", product.idFirebase)
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)*/
        }

        holder.btnDelete.setOnClickListener {
            onClickDelete(product.idFirebase)
        }

        holder.btnUpdate.setOnClickListener {
            onClickUpdate(product.idFirebase)
        }
    }

    fun setData(products: List<Product>) {
        productsList.clear()
        productsListFiltered.clear()
        productsList.addAll(products)
        productsListFiltered.addAll(products)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) productsListFiltered = productsList else {
                    val filteredList = ArrayList<Product>()
                    productsList
                        .filter {
                            (it.nombre.contains(constraint!!, true)) or (it.descripcion.contains(constraint, true))

                        }
                        .forEach { filteredList.add(it) }
                    productsListFiltered = filteredList

                }
                return FilterResults().apply { values = productsListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                productsListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }
}
