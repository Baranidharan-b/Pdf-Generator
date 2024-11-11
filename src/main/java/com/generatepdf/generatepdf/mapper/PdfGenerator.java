package com.generatepdf.generatepdf.mapper;
import com.itextpdf.html2pdf.HtmlConverter;

import java.io.IOException;
import java.io.OutputStream;
/**
 * This class provides a method to generate a PDF document from an HTML string.
 */
public class PdfGenerator {

    /**
     * Converts an HTML string into a PDF and writes it to the provided output stream.
     *
     * @param html The HTML string to be converted into a PDF.
     * @param outputStream The output stream where the generated PDF will be written.
     * @throws IOException If an I/O error occurs while writing to the output stream.
     */
    public static void generatePdfFromHtml(String html, OutputStream outputStream) throws IOException {
        HtmlConverter.convertToPdf(html, outputStream);
    }
}
