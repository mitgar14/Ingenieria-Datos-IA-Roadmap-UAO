package com.example.apiDocsTICS.Model.Documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import org.bson.types.ObjectId;

@Data

@NoArgsConstructor
public class Vista {
    private ObjectId usuarioId;
    private Date fechaVista;
    public Vista(ObjectId usuarioId, Date fechaVista) {
        this.usuarioId = usuarioId;
        this.fechaVista = fechaVista;
    }
}
