package com.example.apiDocsTICS.Controller;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.apiDocsTICS.Model.DescargaModel;
import com.example.apiDocsTICS.Service.IDescargaService;
import java.util.List;

@RestController
@RequestMapping("/descargas")
public class DescargaController {

    @Autowired
    private IDescargaService descargaService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearDescarga(@RequestBody DescargaModel descarga) {
        try {
            return new ResponseEntity<>(descargaService.crearDescarga(descarga), HttpStatus.CREATED);
        } catch (RecursoNoEncontradoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<DescargaModel> obtenerDescargaPorId(@PathVariable int id) {
        try {
            return new ResponseEntity<>(descargaService.obtenerDescargaPorId(id), HttpStatus.OK);
        }catch (RecursoNoEncontradoException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/todas")
    public ResponseEntity<List<DescargaModel>> obtenerDescargas() {
        return new ResponseEntity<>(descargaService.obtenerDescargas(), HttpStatus.OK);
    }

}
