package com.example.apiDocsTICS.Service;

import java.util.List;

import com.example.apiDocsTICS.Model.CategoriaModel;

public interface ICategoriaService {
    String crearCategoria(CategoriaModel categoria);
    String eliminarCategoriaPorId(int idCategoria);
    String modificarCategoriaPorId(int idCategoria, CategoriaModel categoria);
    CategoriaModel obtenerCategoriaPorId(int idCategoria);
    List<CategoriaModel> obtenerCategorias();
}
