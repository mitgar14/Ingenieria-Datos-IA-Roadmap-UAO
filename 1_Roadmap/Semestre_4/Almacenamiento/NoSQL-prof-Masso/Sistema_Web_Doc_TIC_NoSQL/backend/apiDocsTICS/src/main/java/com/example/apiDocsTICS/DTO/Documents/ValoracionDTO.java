package com.example.apiDocsTICS.DTO.Documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValoracionDTO {
    private String usuarioId;
    private Integer valoracion;
    private Date fechaValora;
}
