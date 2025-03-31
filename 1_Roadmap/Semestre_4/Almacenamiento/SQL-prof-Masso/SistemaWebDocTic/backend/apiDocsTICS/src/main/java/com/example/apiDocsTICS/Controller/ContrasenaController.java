package com.example.apiDocsTICS.Controller;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.apiDocsTICS.Model.ContrasenaModel;
import com.example.apiDocsTICS.Service.IContrasenaService;
@RestController
@RequestMapping("/contrasena")
public class ContrasenaController {
    @Autowired
    private IContrasenaService contrasenaService;

    @GetMapping("/activa/{idUsuario}")
    public ResponseEntity<String> obtenerContrasenaActiva(@PathVariable Integer idUsuario) {
        try {
            String contrasenaActiva = contrasenaService.obtenerContrasenaActivaUsuario(idUsuario);
            return new ResponseEntity<>(contrasenaActiva, HttpStatus.OK);
        } catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("La peticion que intenta hacer se ha escrito de forma incorrecta \n"+"["+e.getMessage()+"]", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/crear")
    public ResponseEntity<String> crearContrasena(@RequestBody ContrasenaModel contrasena){
        try{
        return new ResponseEntity<>(contrasenaService.crearContrasena(contrasena), HttpStatus.OK);
        }catch (RecursoNoEncontradoException e){
            return new ResponseEntity<>("La peticion que intenta hacer se ha escrito de forma incorrecta \n"+"["+e.getMessage()+"]", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
