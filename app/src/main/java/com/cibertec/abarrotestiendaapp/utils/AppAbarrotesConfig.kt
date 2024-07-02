package com.cibertec.abarrotestiendaapp.utils

import android.app.Application
import android.content.Context
import android.util.Log
import com.cibertec.abarrotestiendaapp.data.InitBD
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AppAbarrotesConfig : Application(){
    companion object{
        lateinit var CONTEXT:Context
        lateinit var BD: InitBD
        lateinit var databaseReference: DatabaseReference
        var isConnectNetwork = true
        var IS_ONLINE: Boolean = false
        var BD_NAME="tienda.bd"
        var VERSION=1
    }
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        CONTEXT =applicationContext
        BD = InitBD()
        databaseReference = FirebaseDatabase.getInstance().reference
        validateConnectividad(){
            isConnectNetwork = it
        }
    }

    private fun validateConnectividad(isConnect:(Boolean) -> Unit){
        FirebaseDatabase.getInstance().getReference(".info/connected").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                isConnect.invoke(connected)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase", "Error al obtener estado de conexión", error.toException())
                isConnect.invoke(false)
            }
        })
    }

}