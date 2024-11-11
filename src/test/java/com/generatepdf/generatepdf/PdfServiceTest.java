package com.generatepdf.generatepdf;

import com.generatepdf.generatepdf.model.InvoiceRequest;
import com.generatepdf.generatepdf.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class PdfServiceTest {

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private PdfService pdfService;

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
    public void testThymeleafTemplateProcessing() {
        Context context = new Context();
        context.setVariable("name", "John Doe");

        String result = templateEngine.process("<html><body>Hello, ${name}!</body></html>", context);
        assertEquals("<html><body>Hello, John Doe!</body></html>", result);
    }
    @Test
    public void testGenerateOrRetrievePdf_RetrievesExistingPdf() throws IOException, NoSuchAlgorithmException {
        // Mock template rendering
        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("<html><body>Test PDF</body></html>");

        // Generate PDF the first time
        File generatedFile = pdfService.generateOrRetrievePdf(request);
        long fileSize = generatedFile.length();

        // Call the method again to check if it retrieves the cached version
        File cachedFile = pdfService.generateOrRetrievePdf(request);

        // Verify that the same file is returned (caching worked)
        assertEquals(fileSize, cachedFile.length());
        assertTrue(cachedFile.exists());

        // Clean up
        cachedFile.delete();
    }
}
