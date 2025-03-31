package com.example.apiDocsTICS.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.DTO.CategoriasResponseDTO;
import com.example.apiDocsTICS.Exception.ForeignKeyConstraintException;
import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.CategoriasModel;
import com.example.apiDocsTICS.Repository.ICategoriasRepository;

@Service
public class CategoriasServiceImp implements ICategoriasService {
    @Autowired 
    private ICategoriasRepository categoriaRepository;

    @Override
    public String crearCategoria(CategoriasModel categoria) {
        // Validar que si existe subIdCategoria, la categoría padre exista
        if (categoria.getSubIdCategoria() != null) {
            if (!ObjectId.isValid(categoria.getSubIdCategoria().toString())) {
                throw new IllegalArgumentException("El subIdCategoria debe ser un ObjectId válido de 24 caracteres hexadecimales");
            }
            categoriaRepository.findById(categoria.getSubIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria padre no encontrada con el Id: " + categoria.getSubIdCategoria()));
        }
        
        categoriaRepository.save(categoria);
        return "La categoria con id " + categoria.get_id() + " y nombre " + categoria.getNombre() + " ha sido creada";
    }

    @Override
    public String eliminarCategoriaPorId(ObjectId categoriaId) {
        Optional<CategoriasModel> categoriaEncontrada = categoriaRepository.findById(categoriaId);
        if (categoriaEncontrada.isPresent()) {
            categoriaRepository.delete(categoriaEncontrada.get());
            return "La categoria con id " + categoriaId + " fue eliminada con exito";
        } else {
            throw new RecursoNoEncontradoException("Categoria no encontrada con el Id " + categoriaId);
        }
    }

    @Override
    public CategoriasResponseDTO obtenerCategoriaPorId(ObjectId categoriaId) {
        Optional<CategoriasModel> categoriaEncontrada = categoriaRepository.findById(categoriaId);
        CategoriasModel categoria = categoriaEncontrada.orElseThrow(() -> new RecursoNoEncontradoException("La categoria con el id " + categoriaId + " no fue encontrada"));

        CategoriasResponseDTO responseDTO = new CategoriasResponseDTO();
        responseDTO.setNombre(categoria.getNombre());

        if (categoria.getSubIdCategoria() != null) {
            Optional<CategoriasModel> categoriaPadre = categoriaRepository.findById(categoria.getSubIdCategoria());
            categoriaPadre.ifPresent(c -> responseDTO.setNombreCategoriaPadre(c.getNombre()));
        }

        return responseDTO;
    }

    @Override
    public List<CategoriasResponseDTO> obtenerCategorias() {
        List<CategoriasModel> categorias = categoriaRepository.findAll();
        return categorias.stream().map(categoria -> {
            CategoriasResponseDTO responseDTO = new CategoriasResponseDTO();
            responseDTO.setNombre(categoria.getNombre());
            if (categoria.getSubIdCategoria() != null) {
                Optional<CategoriasModel> categoriaPadre = categoriaRepository.findById(categoria.getSubIdCategoria());
                categoriaPadre.ifPresent(c -> responseDTO.setNombreCategoriaPadre(c.getNombre()));
            }
            return responseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public String modificarCategoriaPorId(ObjectId categoriaId, CategoriasModel categoria) {
        try {
            Optional<CategoriasModel> categoriaEncontrada = categoriaRepository.findById(categoriaId);
            if (categoriaEncontrada.isPresent()) {
                CategoriasModel categoriaModificada = categoriaEncontrada.get();
                categoriaModificada.setNombre(categoria.getNombre());
                categoriaModificada.setSubIdCategoria(categoria.getSubIdCategoria());
                categoriaRepository.save(categoriaModificada);
                return "La categoria con id " + categoriaId + " ha sido modificada";
            } else {
                throw new RecursoNoEncontradoException("Categoria no encontrada con el Id " + categoriaId);
            }
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintException("categoriaId al que le apunta el subcategoriaId no fue encontrada con el ID: " + categoria.getSubIdCategoria());
        }
    }
}