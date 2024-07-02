package com.cibertec.abarrotestiendaapp.entity

class Product {
    var idFirebase: String = ""
    var nombre: String = ""
    var descripcion: String = ""
    var precio: Double = 0.0
    var categoria: String = ""
    var imagenUrl: String = ""

    constructor(idFirebase: String = "",nombre: String, descripcion: String, precio: Double, categoria: String, imagenUrl: String = "") {
        this.nombre = nombre
        this.descripcion = descripcion
        this.precio = precio
        this.categoria = categoria
        this.imagenUrl = imagenUrl
        this.idFirebase = idFirebase
    }

    constructor()
}