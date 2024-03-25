package com.example.demo.service;

import com.example.demo.documentInterface.DocumentReadable;
import com.example.demo.modelo.CsvUser;
import com.example.demo.modelo.Document;
import com.example.demo.modelo.User;
import com.example.demo.modelo.Validador;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvDocumentService implements DocumentReadable {

    @Autowired
    private CsvUser csvUser;
    @Autowired
    private Document document;
    @Autowired
    private WebClient webClient;

    public CsvDocumentService(CsvUser csvUser, Document document, WebClient webClient) {
        this.csvUser = csvUser;
        this.document = document;
        this.webClient = webClient;
    }

    @Override
    public Validador readDcoument(Document document) {

            Validador validador = new Validador();
            int lineasValidas = 0;
            int lineasInvalidas = 0;
            CSVReader cvsReader = null;
            try {
                cvsReader = new CSVReader(new FileReader(document.getRuta()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            List<User> listaDocumentos = new ArrayList<>();
            List<String[]> lista = null;
            try {
                lista = cvsReader.readAll();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
            for (String[] array : lista) {
                csvUser = new CsvUser(array[1],array[2], array[3], array[6], array[6], array[7], array[8]);
                listaDocumentos.add(csvUser);
            }

        for (User user : listaDocumentos){

            Mono<Boolean> respuestaMono = webClient.post()
                    .uri("csv")
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(Boolean.class);

            if (Boolean.TRUE.equals(respuestaMono.block())){
                lineasValidas++;
            }else {
                lineasInvalidas++;
            }
            validador.setLineasNoValidas(lineasInvalidas);
            validador.setLineasValidas(lineasValidas);
        }
            return validador;
    }
}

