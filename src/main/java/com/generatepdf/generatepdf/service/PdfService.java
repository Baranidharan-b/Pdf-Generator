package com.generatepdf.generatepdf.service;

import com.generatepdf.generatepdf.mapper.PdfGenerator;
import com.generatepdf.generatepdf.model.InvoiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This service class is responsible for generating or retrieving PDF files based on invoice requests.
 * It uses a TemplateEngine to convert Thymeleaf templates into HTML content, and then generates a PDF
 * from the HTML using a custom PDF generator. The generated PDFs are cached based on a hash of the
 * invoice request data to improve performance.
 */
@Service
public class PdfService {
    private final TemplateEngine templateEngine;
    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);

    /**
     * Constructor for the PdfService class.
     *
     * @param templateEngine The TemplateEngine used to convert Thymeleaf templates into HTML content.
     */
    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Generates or retrieves a PDF file based on the given invoice request.
     *
     * @param request The invoice request data.
     * @return The generated or retrieved PDF file.
     * @throws IOException If an error occurs while generating or retrieving the PDF file.
     */
    public File generateOrRetrievePdf(InvoiceRequest request) throws IOException {
        try {
            String hash = generateHash(request);
            File pdfFile = new File("C:/Users/bdpri/Downloads/generatepdf/generatepdf/output/" + hash + ".pdf");

            // Check if the PDF already exists
            if (pdfFile.exists()) {
                logger.info("Returning cached PDF for request: {}", request);
                return pdfFile;
            }

            // Generate a new PDF
            logger.info("Generating new PDF for request: {}", request);
            Context context = new Context();
            context.setVariable("request", request);

            // Convert Thymeleaf template to HTML
            String htmlContent = templateEngine.process("invoice", context);

            // Check if HTML content is null or empty
            if (htmlContent == null || htmlContent.isEmpty()) {
                logger.error("Generated HTML content is null or empty, cannot proceed with PDF generation.");
                throw new IllegalArgumentException("HTML content for PDF generation is null or empty");
            }

            // Generate PDF from HTML content
            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
                PdfGenerator.generatePdfFromHtml(htmlContent, fos);
                logger.info("PDF generated successfully and saved at: {}", pdfFile.getPath());
            }

            return pdfFile;
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error generating hash for request", e);
            throw new IOException("Failed to generate hash for PDF filename", e);
        } catch (IOException e) {
            logger.error("Error creating PDF file", e);
            throw new IOException("Failed to generate or save PDF file", e);
        }
    }

    private String generateHash(InvoiceRequest request) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");

        // Build hash source string manually for better control
        StringBuilder data = new StringBuilder();
        data.append(request.getSeller())
                .append(request.getSellerGstin())
                .append(request.getSellerAddress())
                .append(request.getBuyer())
                .append(request.getBuyerGstin())
                .append(request.getBuyerAddress());

        // Now request.getItems() will never return null
        request.getItems().forEach(item -> {
            data.append(item.getName())
                    .append(item.getQuantity())
                    .append(item.getRate())
                    .append(item.getAmount());
        });

        byte[] hashBytes = digest.digest(data.toString().getBytes());
        return javax.xml.bind.DatatypeConverter.printHexBinary(hashBytes);
    }

}
