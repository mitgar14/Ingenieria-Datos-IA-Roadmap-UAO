package com.example.apiDocsTICS.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiDocsTICS.DTO.CategoriasResponseDTO;
import com.example.apiDocsTICS.Model.CategoriasModel;
import com.example.apiDocsTICS.Service.ICategoriasService;





@RestController
@RequestMapping("/categorias")
public class CategoriasController {
    @Autowired
    ICategoriasService categoriaService;

    @PostMapping("/post")
    public ResponseEntity<String> crearCategoria(@RequestBody CategoriasModel categoria) {
        categoriaService.crearCategoria(categoria);
        return ResponseEntity.ok().body(categoriaService.crearCategoria(categoria));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarCategoriaPorId(@PathVariable String id) {
        try {
            ObjectId categoriaId = new ObjectId(id);
            categoriaService.eliminarCategoriaPorId(categoriaId);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> modificarCategoriaPorId(@PathVariable String id, @RequestBody CategoriasModel categoria) {
        try {
            ObjectId categoriaId = new ObjectId(id);
            categoriaService.modificarCategoriaPorId(categoriaId, categoria);
            return ResponseEntity.ok().body(categoriaService.modificarCategoriaPorId(categoriaId, categoria));
        } catch (Exception e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> obtenerCategoriaPorId(@PathVariable String id) {
        try {
            ObjectId categoriaId = new ObjectId(id);
            CategoriasResponseDTO categoria = categoriaService.obtenerCategoriaPorId(categoriaId);
            return ResponseEntity.ok().body(categoria);
        } catch (Exception e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> obtenerCategorias() {
        try {
            List<CategoriasResponseDTO> categorias = categoriaService.obtenerCategorias();
            return ResponseEntity.ok().body(categorias);
        } catch (Exception e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }
}