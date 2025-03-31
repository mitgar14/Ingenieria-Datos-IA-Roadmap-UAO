package com.example.apiDocsTICS.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiDocsTICS.DTO.AccionUsuarioDTO;
import com.example.apiDocsTICS.DTO.DocumentosDTO;
import com.example.apiDocsTICS.DTO.Documents.CategoriaDTO;
import com.example.apiDocsTICS.DTO.Documents.DescargaDTO;
import com.example.apiDocsTICS.DTO.Documents.InfoAutorDTO;
import com.example.apiDocsTICS.DTO.Documents.ValoracionDTO;
import com.example.apiDocsTICS.DTO.Documents.VistaDTO;
import com.example.apiDocsTICS.Exception.AccesoNoPermitidoException;
import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.DocumentosModel;
import com.example.apiDocsTICS.Model.Documents.Categoria;
import com.example.apiDocsTICS.Model.Documents.Descarga;
import com.example.apiDocsTICS.Model.Documents.InfoAutor;
import com.example.apiDocsTICS.Model.Documents.Valoracion;
import com.example.apiDocsTICS.Model.Documents.Vista;
import com.example.apiDocsTICS.Service.IDocumetosService;

@RestController
@RequestMapping("/documentos")
public class DocumentosController {

    @Autowired
    private IDocumetosService documentosService;

    @PostMapping("/insertar")
    public ResponseEntity<String> crearDocumento(@RequestBody DocumentosDTO documentoDTO) {
        DocumentosModel documentoModel = mapDTOToModel(documentoDTO);
        String resultado = documentosService.CrearDocumento(documentoModel);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarDocumentoPorId(@PathVariable String id) {
        try {
            ObjectId documentoId = new ObjectId(id);
            DocumentosModel documento = documentosService.buscarDocumento(documentoId);
            DocumentosDTO documentoDTO = mapModelToDTO(documento);
            return ResponseEntity.ok(documentoDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DocumentosDTO>> listarDocumentos() {
        List<DocumentosModel> documentos = documentosService.listarDocumentos();
        List<DocumentosDTO> documentosDTO = documentos.stream()
                .map(this::mapModelToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(documentosDTO, HttpStatus.OK);
    }


    @PostMapping("/{documentoId}/valorar")
    public ResponseEntity<?> valorarDocumento(
            @PathVariable String documentoId,
            @RequestBody ValoracionDTO valoracionDTO) {

        try {
            ObjectId docId = new ObjectId(documentoId);
            ObjectId userId = new ObjectId(valoracionDTO.getUsuarioId());

            String resultado = documentosService.valorarDocumento(docId, userId, valoracionDTO);

            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccesoNoPermitidoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al valorar el documento");
        }
    }

    @PostMapping("/{documentoId}/ver")
    public ResponseEntity<?> registrarVista(
            @PathVariable String documentoId,
            @RequestBody AccionUsuarioDTO accionUsuarioDTO) {

        try {
            ObjectId docId = new ObjectId(documentoId);
            ObjectId userId = new ObjectId(accionUsuarioDTO.getUsuarioId());

            String resultado = documentosService.registrarVista(docId, userId);

            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccesoNoPermitidoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la vista");
        }
    }

    @PostMapping("/{documentoId}/descargar")
    public ResponseEntity<?> registrarDescarga(
            @PathVariable String documentoId,
            @RequestBody AccionUsuarioDTO accionUsuarioDTO) {

        try {
            ObjectId docId = new ObjectId(documentoId);
            ObjectId userId = new ObjectId(accionUsuarioDTO.getUsuarioId());

            String resultado = documentosService.registrarDescarga(docId, userId);

            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccesoNoPermitidoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la descarga");
        }
    }



    // Métodos de ayuda para mapear entre Model y DTO
    private DocumentosModel mapDTOToModel(DocumentosDTO dto) {
        DocumentosModel model = new DocumentosModel();
        model.set_id(dto.getId());
        model.setTituloDoc(dto.getTituloDoc());
        model.setVisibilidad(dto.getVisibilidad());
        model.setURL(dto.getURL());
        model.setDescripcion(dto.getDescripcion());
        if (dto.getCategorias() != null) {
            model.setCategorias(dto.getCategorias().stream()
                    .map(this::mapCategoriaDTOToModel)
                    .collect(Collectors.toList()));
        }
        if (dto.getDescargas() != null) {
            model.setDescargas(dto.getDescargas().stream()
                    .map(this::mapDescargaDTOToModel)
                    .collect(Collectors.toList()));
        }
        if (dto.getVistas() != null) {
            model.setVistas(dto.getVistas().stream()
                    .map(this::mapVistaDTOToModel)
                    .collect(Collectors.toList()));
        }
        if (dto.getValoraciones() != null) {
            model.setValoraciones(dto.getValoraciones().stream()
                    .map(this::mapValoracionDTOToModel)
                    .collect(Collectors.toList()));
        }
        if (dto.getInfoAutores() != null) {
            model.setInfoAutores(dto.getInfoAutores().stream()
                    .map(this::mapInfoAutorDTOToModel)
                    .collect(Collectors.toList()));
        }
        model.setAbstracto(dto.getAbstracto());
        return model;
    }

    private DocumentosDTO mapModelToDTO(DocumentosModel model) {
        DocumentosDTO dto = new DocumentosDTO();
        dto.setId(model.get_id());
        dto.setTituloDoc(model.getTituloDoc());
        dto.setVisibilidad(model.getVisibilidad());
        dto.setURL(model.getURL());
        dto.setDescripcion(model.getDescripcion());
        if (model.getCategorias() != null) {
            dto.setCategorias(model.getCategorias().stream()
                    .map(this::mapCategoriaModelToDTO)
                    .collect(Collectors.toList()));
        }
        if (model.getDescargas() != null) {
            dto.setDescargas(model.getDescargas().stream()
                    .map(this::mapDescargaModelToDTO)
                    .collect(Collectors.toList()));
        }
        if (model.getVistas() != null) {
            dto.setVistas(model.getVistas().stream()
                    .map(this::mapVistaModelToDTO)
                    .collect(Collectors.toList()));
        }
        if (model.getValoraciones() != null) {
            dto.setValoraciones(model.getValoraciones().stream()
                    .map(this::mapValoracionModelToDTO)
                    .collect(Collectors.toList()));
        }
        if (model.getInfoAutores() != null) {
            dto.setInfoAutores(model.getInfoAutores().stream()
                    .map(this::mapInfoAutorModelToDTO)
                    .collect(Collectors.toList()));
        }
        dto.setAbstracto(model.getAbstracto());
        return dto;
    }

    // Métodos de mapeo para documentos embebidos
    private Categoria mapCategoriaDTOToModel(CategoriaDTO dto) {
        return new Categoria(new ObjectId(dto.getCategoriaId()));
    }

    private CategoriaDTO mapCategoriaModelToDTO(Categoria model) {
        return new CategoriaDTO(model.getCategoriaId().toHexString());
    }

    private Descarga mapDescargaDTOToModel(DescargaDTO dto) {
        return new Descarga(
                new ObjectId(dto.getUsuarioId()),
                dto.getFechaDescarga()
        );
    }

    private DescargaDTO mapDescargaModelToDTO(Descarga model) {
        return new DescargaDTO(
                model.getUsuarioId().toHexString(),
                model.getFechaDescarga()
        );
    }

    private Vista mapVistaDTOToModel(VistaDTO dto) {
        return new Vista(
                new ObjectId(dto.getUsuarioId()),
                dto.getFechaVista()
        );
    }

    private VistaDTO mapVistaModelToDTO(Vista model) {
        return new VistaDTO(
                model.getUsuarioId().toHexString(),
                model.getFechaVista()
        );
    }

    private Valoracion mapValoracionDTOToModel(ValoracionDTO dto) {
        return new Valoracion(
                new ObjectId(dto.getUsuarioId()),
                dto.getValoracion(),
                dto.getFechaValora()
        );
    }

    private ValoracionDTO mapValoracionModelToDTO(Valoracion model) {
        return new ValoracionDTO(
                model.getUsuarioId().toHexString(),
                model.getValoracion(),
                model.getFechaValora()
        );
    }

    private InfoAutor mapInfoAutorDTOToModel(InfoAutorDTO dto) {
        return new InfoAutor(
                new ObjectId(dto.getUsuarioId()),
                dto.getFechaPublicacion(),
                dto.getBiografia(),
                dto.getRol()
        );
    }

    private InfoAutorDTO mapInfoAutorModelToDTO(InfoAutor model) {
        return new InfoAutorDTO(
                model.getUsuarioId().toHexString(),
                model.getFechaPublicacion(),
                model.getBiografia(),
                model.getRol()
        );
    }
}
