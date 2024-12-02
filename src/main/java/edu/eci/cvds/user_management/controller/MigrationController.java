package edu.eci.cvds.user_management.controller;
import edu.eci.cvds.user_management.model.Responsible;
import edu.eci.cvds.user_management.model.Student;
import edu.eci.cvds.user_management.service.FindService;
import edu.eci.cvds.user_management.service.RegisterService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping
public class MigrationController {
    private final RegisterService registerService;
    private final FindService findService;

    public MigrationController(RegisterService registerService, FindService findService) {
        this.registerService = registerService;
        this.findService = findService;
    }

    @PostMapping("/migrate-data")
    public String migrateData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "The file is empty.";
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String studentId = String.valueOf((int)row.getCell(0).getNumericCellValue());
                String studentName = row.getCell(1).getStringCellValue();
                String studentDocument = String.valueOf((int)row.getCell(2).getNumericCellValue());
                String studentDocumentType = row.getCell(3).getStringCellValue();
                String responsibleName = row.getCell(4).getStringCellValue();
                String responsibleDocNumber = String.valueOf((int)row.getCell(5).getNumericCellValue());
                String responsibleDocSite = row.getCell(6).getStringCellValue();
                String responsiblePhone = String.valueOf((int)row.getCell(7).getNumericCellValue());;
                String responsibleEmail = row.getCell(8).getStringCellValue();
                String studentCourse = (row.getCell(9).getCellType() == CellType.NUMERIC)
                        ? String.valueOf((int) row.getCell(9).getNumericCellValue())
                        : row.getCell(9).getStringCellValue().trim();


                Optional<Responsible> existingResponsible = Optional.ofNullable(findService.findResponsibleByDocument(responsibleDocNumber));
                Responsible responsible;
                if (existingResponsible.isPresent()) {
                    responsible = existingResponsible.get();
                } else {
                    responsible = new Responsible(responsibleDocNumber, responsibleDocSite, responsibleName, responsiblePhone, responsibleEmail);
                    registerService.registerResponsible(responsible);
                }

                Student student = new Student(studentId, studentName, studentDocument, studentDocumentType, studentCourse,responsibleDocNumber);
                registerService.registerStudent(student);
            }

            return "Data migration completed successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred during data migration: " + e.getMessage();
        }
    }


    @GetMapping("/download-template")
    public ResponseEntity<byte[]> downloadTemplate() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("baseDeDatosEstudiantes");

            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "codigoEstudiante",
                    "nombreEstudiante",
                    "numeroDocumentoEstudiante",
                    "tipoDoc",
                    "nombreResponsable",
                    "documentoResponsable",
                    "expedicionDocResponsable",
                    "numeroTelefonico",
                    "correoResponsable",
                    "Curso",
                    "Grado"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=baseDeDatosEstudiantes.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
