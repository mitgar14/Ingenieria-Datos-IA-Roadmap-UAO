package com.example.apiDocsTICS.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.example.apiDocsTICS.Model.ENUM.Visibilidad;
import com.example.apiDocsTICS.DTO.Documents.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentosDTO {
    private String id;
    private String tituloDoc;
    private Visibilidad visibilidad;
    @JsonProperty("URL")
    private String URL;
    private String descripcion;
    private List<CategoriaDTO> categorias;
    private List<DescargaDTO> descargas;
    private List<VistaDTO> vistas;
    private List<ValoracionDTO> valoraciones;
    private List<InfoAutorDTO> infoAutores;
    private String abstracto;
}
