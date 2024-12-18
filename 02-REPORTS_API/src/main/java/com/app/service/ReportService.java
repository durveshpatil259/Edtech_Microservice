package com.app.service;

import com.app.request.SearchRequest;
import com.app.response.SearchResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ReportService {

    public List<String> getUniquePlanNames();

    public List<String> getUniquePlanStatus();

    public List<SearchResponse> search(SearchRequest searchRequest);

    public void generateExcel(HttpServletResponse response) throws IOException;

    public void generatePdf(HttpServletResponse response) throws Exception;


}
