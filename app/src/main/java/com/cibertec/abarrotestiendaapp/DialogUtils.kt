package com.cibertec.abarrotestiendaapp

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


fun showAlertDialog(context: Context, men: String, onClick: DialogInterface.OnClickListener? = null )  {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Alerta")
    builder.setMessage(men)
    builder.setPositiveButton("Aceptar", onClick)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}
