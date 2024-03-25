package com.example.demo.service;

import com.example.demo.documentInterface.DocumentReadable;
import com.example.demo.modelo.Document;
import com.example.demo.modelo.User;
import com.example.demo.modelo.Validador;
import com.example.demo.modelo.XlsxUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class XlsxDocumentService implements DocumentReadable {

    @Autowired
    private WebClient webClient;
    @Autowired
    private XlsxUser xlsxUser;

    public XlsxDocumentService(WebClient webClient, XlsxUser xlsxUser) {
        this.webClient = webClient;
        this.xlsxUser = xlsxUser;
    }

    @Override
    public Validador readDcoument(Document document) throws IOException {

        Validador validador = new Validador();
        int lineasValidas = 0;
        int lineasInvalidas = 0;

        FileInputStream fileInputStream = new FileInputStream(document.getRuta());
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        int numberOfSheets = workbook.getNumberOfSheets();

        XSSFSheet xssfSheet = workbook.getSheet("SafetyData");

        List<User> listaDocumentos = new ArrayList<>();
        for (int j = 1; j <= xssfSheet.getLastRowNum(); j++) {
            XSSFRow row = xssfSheet.getRow(j);
            if (row != null) {
                XlsxUser xlsxUser = new XlsxUser(
                        row.getCell(0).toString(),
                        row.getCell(1).toString(),
                        row.getCell(2).toString(),
                        row.getCell(3).toString(),
                        row.getCell(4).toString(),
                        row.getCell(5).toString(),
                        row.getCell(6).toString(),
                        row.getCell(7).toString(),
                        row.getCell(8).toString(),
                        row.getCell(9).toString(),
                        row.getCell(10).toString(),
                        row.getCell(11).toString(),
                        row.getCell(12).toString(),
                        row.getCell(13).toString());
                listaDocumentos.add(xlsxUser);
            }
        }
        for (User user : listaDocumentos){

            Mono<Boolean> respuestaMono = webClient.post()
                    .uri("xlsx")
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
