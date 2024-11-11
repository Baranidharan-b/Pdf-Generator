package com.generatepdf.generatepdf;
import com.generatepdf.generatepdf.controller.PdfController;
import com.generatepdf.generatepdf.model.InvoiceRequest;
import com.generatepdf.generatepdf.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PdfControllerTest {

    @Mock
    private PdfService pdfService;

    @InjectMocks
    private PdfController pdfController;

    private InvoiceRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new InvoiceRequest();
        request.setSeller("XYZ Pvt. Ltd.");
        request.setSellerGstin("29AABBCCDD121ZD");
        request.setSellerAddress("New Delhi, India");
        request.setBuyer("Vedant Computers");
        request.setBuyerGstin("29AABBCCDD131ZD");
        request.setBuyerAddress("New Delhi, India");
        request.setItems(new ArrayList<>());
    }

    @Test
    public void testGeneratePdf_ReturnsOk() throws IOException, NoSuchAlgorithmException {
        // Mocking the response from PdfService
        File mockFile = File.createTempFile("test", ".pdf");
        when(pdfService.generateOrRetrievePdf(any(InvoiceRequest.class))).thenReturn(mockFile);

        // Call the endpoint
        ResponseEntity<?> response = pdfController.generatePdf(request);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("application/pdf", response.getHeaders().getContentType().toString());

        // Clean up
        mockFile.delete();
    }


@Test
public void testGenerateOrRetrievePdfThrowsIOException() throws IOException, NoSuchAlgorithmException {
     //Mock exception thrown by the service
    when(pdfService.generateOrRetrievePdf(any(InvoiceRequest.class))).thenThrow(new IOException("File error"));

    assertThrows(IOException.class, () -> {
        pdfController.generatePdf(request);
    });
}
}
