package com.example.demo.controller;

import com.example.demo.modelo.Document;
import com.example.demo.modelo.Validador;
import com.example.demo.service.CsvDocumentService;
import com.example.demo.service.XlsxDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
public class DocumentController {

    private XlsxDocumentService xlsxDocumentService;
    private CsvDocumentService csvDocumentService;

    @Autowired
    public DocumentController(XlsxDocumentService xlsxDocumentService, CsvDocumentService csvDocumentService) {
        this.xlsxDocumentService = xlsxDocumentService;
        this.csvDocumentService = csvDocumentService;
    }

    @PostMapping("/documento/")
    public Validador obtenerUsuarios(@RequestBody Document document) throws IOException {
        if (Objects.equals(document.getTipoArchivo(), "csv")){
            return this.csvDocumentService.readDcoument(document);
        }else {
            return this.xlsxDocumentService.readDcoument(document);
        }
    }
}
