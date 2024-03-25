package com.example.demo.documentInterface;

import com.example.demo.modelo.Document;
import com.example.demo.modelo.Validador;

import java.io.IOException;

public interface DocumentReadable {

    public Validador readDcoument(Document document) throws IOException;
}
