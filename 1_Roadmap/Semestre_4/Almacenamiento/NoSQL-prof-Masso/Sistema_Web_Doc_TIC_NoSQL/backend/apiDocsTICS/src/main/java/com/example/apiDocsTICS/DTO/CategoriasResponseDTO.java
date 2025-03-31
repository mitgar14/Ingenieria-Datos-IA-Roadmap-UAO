package com.example.apiDocsTICS.DTO;

public class CategoriasResponseDTO {
    private String nombre;
    private String nombreCategoriaPadre;

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCategoriaPadre() {
        return nombreCategoriaPadre;
    }

    public void setNombreCategoriaPadre(String nombreCategoriaPadre) {
        this.nombreCategoriaPadre = nombreCategoriaPadre;
    }
}