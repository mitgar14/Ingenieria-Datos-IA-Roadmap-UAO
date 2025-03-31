package com.example.apiDocsTICS.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.Exception.ForeignKeyConstraintException;
import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.CategoriaModel;
import com.example.apiDocsTICS.Repository.ICategoriaRepository;

@Service
public class CategoriaServiceImp implements ICategoriaService {
    @Autowired ICategoriaRepository categoriaRepository;
    @Override
    public String crearCategoria(CategoriaModel categoria) {
            categoriaRepository.save(categoria);
            return "La categoria con id " + categoria.getIdCategoria() + " y nombre " + categoria.getNombre() + " ha sido creada";
    }

    @Override
    public String eliminarCategoriaPorId(int idCategoria) {
        Optional <CategoriaModel> categoriaEncontrada = categoriaRepository.findById(idCategoria);
        if (categoriaEncontrada.isPresent()){
            categoriaRepository.delete(categoriaEncontrada.get());
            return "La categoria con id "+ idCategoria +" fue eliminada con exito";
        } else {
            throw new RecursoNoEncontradoException("Categoria no encontrada con el Id " + idCategoria);
        }
    }

    @Override
    public CategoriaModel obtenerCategoriaPorId(int idCategoria) {
        Optional <CategoriaModel> categoriaEncontrada = categoriaRepository.findById(idCategoria);
        return categoriaEncontrada.orElseThrow(()->new RecursoNoEncontradoException("La categoria con el id "+ idCategoria + " no fue encontrada"));
    }

    @Override
    public List<CategoriaModel> obtenerCategorias() {
        return categoriaRepository.findAll();
    } 

    @Override
    public String modificarCategoriaPorId(int idCategoria, CategoriaModel categoria) {
    try {
        Optional<CategoriaModel> categoriaEncontrada = categoriaRepository.findById(idCategoria);
        if (categoriaEncontrada.isPresent()) {
            CategoriaModel categoriaModificada = categoriaEncontrada.get();
            categoriaModificada.setNombre(categoria.getNombre());
            categoriaModificada.setSubIdCategoria(categoria.getSubIdCategoria());
            categoriaRepository.save(categoriaModificada);
            return "La categoria con id " + idCategoria + " ha sido modificada";
        } else {
            throw new RecursoNoEncontradoException("Categoria no encontrada con el Id " + idCategoria);
        }
    } catch (DataIntegrityViolationException e) {
        throw new ForeignKeyConstraintException("IdCategoria al que le apunta el subIdCategoria no fue encontrada con el ID: " + categoria.getSubIdCategoria().getIdCategoria());
    }
}
}
