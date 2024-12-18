package com.app.service;

import com.app.entity.EligibilityDetails;
import com.app.repository.EligibilityDetailsRepo;
import com.app.request.SearchRequest;
import com.app.response.SearchResponse;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EligibilityDetailsRepo eligibilityDetailsRepo;

    @Override
    public List<String> getUniquePlanNames() {

       return eligibilityDetailsRepo.findPlanNames();

    }

    @Override
    public List<String> getUniquePlanStatus() {
        return eligibilityDetailsRepo.findPlanStatus();
    }

    @Override
    public List<SearchResponse> search(SearchRequest searchRequest) {

        List<SearchResponse> response = new ArrayList<>();

        EligibilityDetails queryBuilder = new EligibilityDetails();

        String planName = searchRequest.getPlanName();

        if(planName !=null && planName.equals("")){
            queryBuilder.setPlanName(planName);
        }

        String planStatus = searchRequest.getPlanStatus();
        if(planStatus !=null && planStatus.equals("")){
            queryBuilder.setPlanStatus(planStatus);
        }

        LocalDate planStartDate = searchRequest.getPlanStartDate();
        if(planStartDate !=null){
            queryBuilder.setPlanStartDate(planStartDate);
        }

        LocalDate planEndDate = searchRequest.getPlanEndDate();
        if(planEndDate !=null){
            queryBuilder.setPlanEndDate(planEndDate);
        }

        Example<EligibilityDetails> example = Example.of(queryBuilder);

        List<EligibilityDetails> entities = eligibilityDetailsRepo.findAll();

        for(EligibilityDetails entity : entities){
            SearchResponse searchResponse = new SearchResponse();
            BeanUtils.copyProperties(entity, searchResponse);
            response.add(searchResponse);
        }
        return response;
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {

        List<EligibilityDetails> entities = eligibilityDetailsRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);


        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Email");
        headerRow.createCell(2).setCellValue("Mobile");
        headerRow.createCell(3).setCellValue("Gender");
        headerRow.createCell(4).setCellValue("SSN");

        int i = 1;
        for(EligibilityDetails entity : entities){

            HSSFRow dataRow = sheet.createRow(i);
            dataRow.createCell(0).setCellValue(entity.getName());
            dataRow.createCell(1).setCellValue(entity.getEmail());
            dataRow.createCell(2).setCellValue(entity.getMobile());
            dataRow.createCell(3).setCellValue(String.valueOf(entity.getGender()));
            dataRow.createCell(4).setCellValue(entity.getSsn());

            i++;
        }
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    public void generatePdf(HttpServletResponse response) throws Exception{

        List<EligibilityDetails> entities = eligibilityDetailsRepo.findAll();

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Search Report",font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 1.0f, 3.0f});
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Name",font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone-No.",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gender",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("SSN",font));
        table.addCell(cell);

        for(EligibilityDetails entity : entities){
            table.addCell(entity.getName());
            table.addCell(entity.getEmail());
            table.addCell(String.valueOf(entity.getMobile()));
            table.addCell(String.valueOf(entity.getGender()));
            table.addCell(String.valueOf(entity.getSsn()));

        }
        document.add(table);
          document.close();
    }
}
