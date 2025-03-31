package com.example.apiDocsTICS.DTO.Documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescargaDTO {
    private String usuarioId;
    private Date fechaDescarga;

}
