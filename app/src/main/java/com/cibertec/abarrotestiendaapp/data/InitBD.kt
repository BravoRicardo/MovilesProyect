package com.cibertec.abarrotestiendaapp.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cibertec.abarrotestiendaapp.utils.AppAbarrotesConfig

class InitBD: SQLiteOpenHelper(AppAbarrotesConfig.CONTEXT, AppAbarrotesConfig.BD_NAME, null, AppAbarrotesConfig.VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE tb_products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idFirebase VARCHAR(150), " +
                    "nombre VARCHAR(100), " +
                    "descripcion TEXT, " +
                    "precio REAL, " +
                    "categoria VARCHAR(50), " +
                    "imagenUrl TEXT)"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}
