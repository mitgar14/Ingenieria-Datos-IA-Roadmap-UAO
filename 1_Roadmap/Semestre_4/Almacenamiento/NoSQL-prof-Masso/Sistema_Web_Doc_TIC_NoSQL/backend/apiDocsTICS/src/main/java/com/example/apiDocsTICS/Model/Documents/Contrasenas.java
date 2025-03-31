package com.example.apiDocsTICS.Model.Documents;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.apiDocsTICS.Model.ENUM.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Contrasenas {
    private String contrasena;
    private Estado estado;
    private LocalDateTime fecha;
}
