package com.app.controller;

import com.app.request.SearchRequest;
import com.app.response.SearchResponse;
import com.app.service.ReportService;
import com.app.service.ReportServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ReportRestController {

    @Autowired
    private ReportService service;

    @GetMapping("/plans")
    public ResponseEntity<List<String>> getPlanNames(){
        List<String> planNames = service.getUniquePlanNames();
        return new ResponseEntity<>(planNames, HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getPlanStatuses(){
        List<String> statuses = service.getUniquePlanStatus();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest searchRequest){
        List<SearchResponse> response = service.search(searchRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/excel")
    public void excelReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=data.xls";
        response.setHeader(headerKey, headerValue);

        service.generateExcel(response);
    }

    @GetMapping("/pdf")
    public void pdfResponse(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=data.pdf";
        response.setHeader(headerKey, headerValue);
        service.generatePdf(response);

    }
}
