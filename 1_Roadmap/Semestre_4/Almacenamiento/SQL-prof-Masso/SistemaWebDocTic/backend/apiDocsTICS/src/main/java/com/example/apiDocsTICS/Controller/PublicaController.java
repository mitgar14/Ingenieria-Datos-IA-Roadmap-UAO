package com.example.apiDocsTICS.Controller;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.apiDocsTICS.Model.PublicaModel;
import com.example.apiDocsTICS.Service.IPublicaService;
import java.util.List;

@RestController
@RequestMapping("/publicaciones")
public class PublicaController {

    @Autowired
    private IPublicaService publicaService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearPublicacion(@RequestBody PublicaModel publicacion) {
        try {
            return new ResponseEntity<>(publicaService.crearPublicacion(publicacion), HttpStatus.CREATED);
        } catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> obtenerPublicacionPorId(@PathVariable int id) {
        try {
            return new ResponseEntity<>(publicaService.obtenerPublicacionPorId(id), HttpStatus.OK);
        } catch (RecursoNoEncontradoException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/todas")
    public ResponseEntity<List<PublicaModel>> obtenerPublicaciones() {
        return new ResponseEntity<>(publicaService.obtenerPublicaciones(), HttpStatus.OK);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificarPublicacion(@PathVariable int id, @RequestBody PublicaModel publicacion) {
        try {
            return new ResponseEntity<>(publicaService.modificarPublicacionPorId(id, publicacion), HttpStatus.OK);
        } catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable int id) {
        try {
            return new ResponseEntity<>(publicaService.eliminarPublicacionPorId(id), HttpStatus.OK);
        } catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
