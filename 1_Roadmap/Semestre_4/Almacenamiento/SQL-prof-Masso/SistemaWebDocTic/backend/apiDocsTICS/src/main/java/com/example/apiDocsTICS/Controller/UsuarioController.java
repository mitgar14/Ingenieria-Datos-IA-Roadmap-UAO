package com.example.apiDocsTICS.Controller;

import java.util.List;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.apiDocsTICS.Model.UsuarioModel;
import com.example.apiDocsTICS.Service.IUsuarioService;

@RestController
@RequestMapping ("/usuarios")
public class UsuarioController {

    @Autowired IUsuarioService usuarioService;
    @PostMapping("/insertar")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioModel usuario) {
        return new ResponseEntity<>(usuarioService.crearUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioId(@PathVariable int id){
        try{
            UsuarioModel usuario = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        }catch (RecursoNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarusuarios")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios(){
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }
    @DeleteMapping("/eliminarusuario/{id}")
    public ResponseEntity<String>  eliminarUsuario(@PathVariable int id){
        try{
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado");
        }catch (RecursoNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
