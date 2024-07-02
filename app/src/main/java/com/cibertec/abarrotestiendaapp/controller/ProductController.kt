package com.cibertec.abarrotestiendaapp.controller
import android.content.ContentValues
import com.cibertec.abarrotestiendaapp.entity.Product
import com.cibertec.abarrotestiendaapp.utils.AppAbarrotesConfig


class ProductController {
    fun findAll(): ArrayList<Product> {
        val lista = arrayListOf<Product>()
        val CN = AppAbarrotesConfig.BD.readableDatabase
        val SQL = "SELECT * FROM tb_products"
        val RS = CN.rawQuery(SQL, null)

        while (RS.moveToNext()) {
            val bean = Product(
                idFirebase = RS.getString(1).toString(),          // idFirebase
                nombre = RS.getString(2),       // nombre
                descripcion = RS.getString(3),       // descripcion
                precio = RS.getDouble(4),       // precio
                categoria = RS.getString(5),       // tipo
                imagenUrl = RS.getString(6)        // imagenUrl
            )
            // Adicionar objeto "bean" dentro de lista
            lista.add(bean)
        }

        // Cerrar cursor y base de datos
        RS.close()
        CN.close()

        return lista
    }


    fun save(bean: Product): Int {
        var estado: Int
        val CN = AppAbarrotesConfig.BD.writableDatabase
        val conte = ContentValues()
        conte.put("idFirebase",bean.idFirebase)
        conte.put("nombre", bean.nombre)
        conte.put("descripcion", bean.descripcion)
        conte.put("precio", bean.precio)
        conte.put("categoria", bean.categoria)
        conte.put("imagenUrl", bean.imagenUrl)

        estado = CN.insert("tb_products", null, conte).toInt()

        // Cerrar base de datos
        CN.close()

        return estado
    }

    fun clearTableProduct() {
        val db = AppAbarrotesConfig.BD.writableDatabase
        db.execSQL("DELETE FROM tb_products")
        db.close()
    }
}
