package com.example.apiDocsTICS.Model.Documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import org.bson.types.ObjectId;

@Data

@NoArgsConstructor
public class Descarga {
    private ObjectId usuarioId;
    private Date fechaDescarga;
    public Descarga(ObjectId usuarioId, Date fechaDescarga) {
        this.usuarioId = usuarioId;
        this.fechaDescarga = fechaDescarga;
    }
}