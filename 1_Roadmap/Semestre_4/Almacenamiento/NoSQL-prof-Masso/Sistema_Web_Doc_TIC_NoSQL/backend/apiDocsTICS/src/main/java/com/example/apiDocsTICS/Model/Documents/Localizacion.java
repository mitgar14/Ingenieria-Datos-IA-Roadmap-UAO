package com.example.apiDocsTICS.Model.Documents;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Localizacion {
    private String ciudadOrigen;
    private String deptoOrigen;
    private String codPostal;
}
