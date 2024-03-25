package com.example.demo.modelo;

import org.springframework.stereotype.Component;

@Component
public class Document {

    private String ruta;
    private String tipoArchivo;

    public Document(String ruta, String tipoArchivo) {
        this.ruta = ruta;
        this.tipoArchivo = tipoArchivo;
    }

    public Document() {
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
}
