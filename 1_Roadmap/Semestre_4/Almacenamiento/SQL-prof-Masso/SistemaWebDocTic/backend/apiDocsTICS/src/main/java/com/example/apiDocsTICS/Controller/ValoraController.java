package com.example.apiDocsTICS.Controller;

import com.example.apiDocsTICS.Exception.RecursoExistente;
import com.example.apiDocsTICS.Exception.ValoracionIncorrecta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiDocsTICS.Service.IValoraService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.ValoraModel;


@RestController
@RequestMapping("/valoraciones")
public class ValoraController {
    @Autowired
    IValoraService valoraService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearValoracion(@RequestBody ValoraModel valora) {
        try {
        return new ResponseEntity<>(valoraService.crearValoracion(valora), HttpStatus.OK);
        }catch (ValoracionIncorrecta e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (RecursoNoEncontradoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (RecursoExistente e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    
    @DeleteMapping("/eliminar/{idValora}")
    public ResponseEntity<?> eliminarValoracionPorId(@PathVariable int idValora) {
        try {
            valoraService.eliminarValoracionPorId(idValora);
            return ResponseEntity.ok().build();
        } catch (RecursoNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/editar/{idValora}")
    public ResponseEntity<?> modificarValoracionPorId(@PathVariable int idValora, @RequestBody ValoraModel valora) {
        try {
            Object valoraActualizada = valoraService.modificarValoracionPorId(idValora, valora);
            if (valoraActualizada != null) {
                return ResponseEntity.ok().body(valoraActualizada);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Valoracion no encontrada con el Id " + idValora);
            }
        }catch (RecursoNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ValoracionIncorrecta e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/obtener/{idValora}")
    public ResponseEntity<?> obtenerValoracionPorId(@PathVariable int idValora) {
        try {
            ValoraModel valora = valoraService.obtenerValoracionPorId(idValora);
            return ResponseEntity.ok(valora);
        } catch (RecursoNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/obtener")
    public ResponseEntity<?> obtenerValoraciones() {
        return ResponseEntity.ok().body(valoraService.obtenerValoraciones());
    }
}
