package com.example.apiDocsTICS.Controller;


import java.util.List;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.apiDocsTICS.Model.MiraModel;
import com.example.apiDocsTICS.Service.IMiraService;


@RestController
@RequestMapping("/mira")
public class MiraController {
    @Autowired
    IMiraService miraService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearMira(@RequestBody MiraModel mira) {
        try {
            return new ResponseEntity<>(miraService.crearMira(mira), HttpStatus.OK);
        }catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtener/documento/{idDocumento}")
    public ResponseEntity<List<MiraModel>> obtenerMiraByDocumento(@PathVariable int idDocumento) {
        try{
            return new ResponseEntity<>(miraService.obtenerMiraByDocumento(idDocumento), HttpStatus.OK);
        }catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/obtener/usuario/{idUsuario}")
    public ResponseEntity<List<MiraModel>> obtenerMiraByUsuario(@PathVariable int idUsuario) {
        try{
            return new ResponseEntity<>(miraService.obtenerMiraByUsuario(idUsuario), HttpStatus.OK);
        }catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/obtener")
    public ResponseEntity<List<MiraModel>> obtenerMira() {
        return new ResponseEntity<>(miraService.obtenerMira(), HttpStatus.OK);
    }
}
