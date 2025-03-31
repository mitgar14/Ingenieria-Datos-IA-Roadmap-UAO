package com.example.apiDocsTICS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiDocsTICS.Service.IDocumentoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.DocumentoModel;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/documentos")
public class DocumentoController {
    @Autowired
    IDocumentoService documentoService;

    @PostMapping("/post")
    public ResponseEntity<String> crearDocumento(@RequestBody DocumentoModel Documento) {
        documentoService.crearDocumento(Documento);
        return new ResponseEntity<>(documentoService.crearDocumento(Documento), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{idDocumento}")
    public ResponseEntity<?> eliminarDocumentoPorId(@PathVariable int idDocumento) {
        try {
            documentoService.eliminarDocumentoPorId(idDocumento);
            return ResponseEntity.ok().build();
        } catch (RecursoNoEncontradoException e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @PutMapping("/put/{idDocumento}")
    public ResponseEntity<?> modificarDocumentoPorId(@PathVariable int idDocumento, @RequestBody DocumentoModel documento) {
        Object documentoActualizado = documentoService.modificarDocumentoPorId(idDocumento, documento);
       if (documentoActualizado != null) {
            return ResponseEntity.ok().body(documentoActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado con el Id " + idDocumento);
        }
    }

    @GetMapping("/get/{idDocumento}")
    public ResponseEntity<?> obtenerDocumentoPorId(@PathVariable int idDocumento) {
        try {
            DocumentoModel documento = documentoService.obtenerDocumentoPorId(idDocumento);
            return ResponseEntity.ok().body(documento);
        } catch (RecursoNoEncontradoException e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> obtenerDocumentos() {
        return ResponseEntity.ok().body(documentoService.obtenerDocumentos());
    }
}
