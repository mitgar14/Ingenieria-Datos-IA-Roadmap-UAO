package com.example.apiDocsTICS.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.example.apiDocsTICS.DTO.CategoriasResponseDTO;
import com.example.apiDocsTICS.Model.CategoriasModel;

public interface ICategoriasService {

    String crearCategoria(CategoriasModel categoria);
    String eliminarCategoriaPorId(ObjectId categoriaId);
    String modificarCategoriaPorId(ObjectId categoriaId, CategoriasModel categoria);
    CategoriasResponseDTO obtenerCategoriaPorId(ObjectId categoriaId);
    List<CategoriasResponseDTO> obtenerCategorias();
}