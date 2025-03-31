package com.example.apiDocsTICS.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.apiDocsTICS.Service.ICategoriaService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.apiDocsTICS.Model.CategoriaModel;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    ICategoriaService categoriaService;

    @PostMapping("/post")
    public ResponseEntity<String> crearCategoria(@RequestBody CategoriaModel categoria) {
        categoriaService.crearCategoria(categoria);
        return ResponseEntity.ok().body(categoriaService.crearCategoria(categoria));
    }

    @DeleteMapping("/delete/{idCategoria}")
    public ResponseEntity<?> eliminarCategoriaPorId(@PathVariable int idCategoria) {
        try {
            categoriaService.eliminarCategoriaPorId(idCategoria);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @PutMapping("/put/{idCategoria}")
    public ResponseEntity<?> modificarCategoriaPorId(@PathVariable int idCategoria, @RequestBody CategoriaModel categoria) {
        try {
            categoriaService.modificarCategoriaPorId(idCategoria, categoria);
            return ResponseEntity.ok().body(categoriaService.modificarCategoriaPorId(idCategoria, categoria));
        } catch (Exception e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @GetMapping("/get/{idCategoria}")
    public ResponseEntity<?> obtenerCategoriaPorId(@PathVariable int idCategoria) {
        try {
            CategoriaModel categoria = categoriaService.obtenerCategoriaPorId(idCategoria);
            return ResponseEntity.ok().body(categoria);
        } catch (Exception e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> obtenerCategorias() {
        try {
            return ResponseEntity.ok().body(categoriaService.obtenerCategorias());
        } catch (Exception e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }
}
