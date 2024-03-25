package com.example.demo.modelo;

import org.springframework.stereotype.Component;

@Component
public class Validador {

    private Integer lineasValidas;
    private Integer lineasNoValidas;

    public Validador() {
    }

    public Validador(Integer lineasValidas, Integer lineasNoValidas) {
        this.lineasValidas = lineasValidas;
        this.lineasNoValidas = lineasNoValidas;
    }

    public Integer getLineasValidas() {
        return lineasValidas;
    }

    public void setLineasValidas(Integer lineasValidas) {
        this.lineasValidas = lineasValidas;
    }

    public Integer getLineasNoValidas() {
        return lineasNoValidas;
    }

    public void setLineasNoValidas(Integer lineasNoValidas) {
        this.lineasNoValidas = lineasNoValidas;
    }
}
