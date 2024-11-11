package com.generatepdf.generatepdf.controller;

import com.generatepdf.generatepdf.model.InvoiceRequest;
import com.generatepdf.generatepdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * This class is responsible for handling PDF generation requests.
 */
@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final PdfService pdfService;

    /**
     * Constructor for the PdfController class.
     *
     * @param pdfService The service responsible for generating or retrieving PDF files.
     */
    @Autowired
    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    /**
     * Endpoint for generating a PDF file based on the provided InvoiceRequest.
     *
     * @param request The request containing the necessary data for generating the PDF.
     * @return A ResponseEntity containing the generated PDF file as an InputStreamResource.
     * @throws IOException If an error occurs while reading or writing to the file.
     * @throws NoSuchAlgorithmException If the algorithm used for hashing is not found.
     */
    @PostMapping("/generate")
    public ResponseEntity<InputStreamResource> generatePdf(@RequestBody InvoiceRequest request) throws IOException, NoSuchAlgorithmException {
        File pdfFile = pdfService.generateOrRetrievePdf(request);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + pdfFile.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}

