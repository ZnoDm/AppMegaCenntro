package com.sinfloo.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.net.URL;

import com.sinfloo.demo.models.DetalleVenta;
import com.sinfloo.demo.models.Venta;
import com.sinfloo.demo.services.VentaService;


@Controller
public class PdfController {
	@Autowired
	VentaService ventaService;
	
    @GetMapping("/pdf/{id}")
    public void generatePdf(@PathVariable("id") Integer id,HttpServletResponse response) throws IOException, DocumentException {
    	Venta venta = ventaService.get(id);
        List<DetalleVenta> detalleVentas = venta.getItems();
        // Set content type and header for PDF response
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"venta-"+venta.getId()+".pdf\"");
        
        // Create a new document
        Document document = new Document(PageSize.A4);
        // Create a PdfWriter instance to write the document content to the response's OutputStream
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        // Open the document
        document.open();
        
        
        // Create a PdfContentByte to draw text
        PdfContentByte contentByte = writer.getDirectContent();

        // Set font styles
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.NORMAL);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        Font detalleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.NORMAL);
        Font detailBoldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.NORMAL);
        Font detailFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);

        // Add title
        Paragraph title = new Paragraph(venta.getTipoDocumento(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        
        
     // Create table for company information and logo
        PdfPTable companyTable = new PdfPTable(2);
        companyTable.setWidthPercentage(100);
        companyTable.setSpacingBefore(10f);
        companyTable.setSpacingAfter(10f);
        float[] columnWidths = { 60f, 40f };
        companyTable.setWidths(columnWidths);

        // Add company information
        PdfPCell companyInfoCell = new PdfPCell();
        companyInfoCell.setBorder(Rectangle.NO_BORDER);
        companyInfoCell.setVerticalAlignment(Element.ALIGN_TOP);
        companyInfoCell.setPaddingBottom(10f);
       
        companyInfoCell.addElement(new Phrase("RUC N° 20440268308", detalleFont));
        companyInfoCell.addElement(new Phrase("BOLETA DE VENTA: 02-000"+ (String.valueOf(venta.getId())), detalleFont));
        companyInfoCell.addElement(new Phrase("MEGACENTRO BOUTIQUE ZOILY E.I.R.L.", subtitleFont));
        companyInfoCell.addElement(new Phrase("Cal. Lima Nro. 565 Cercado - Chepen, La Libertad, Perú", subtitleFont));
        companyInfoCell.addElement(new Phrase("Telefono: 044 561785", subtitleFont));
        companyTable.addCell(companyInfoCell);

        // Add company logo
        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        Image logo = Image.getInstance(new URL("https://cdn.shopify.com/s/files/1/0530/6987/3301/files/Diseno_Megacentro_para_web_3_d57f43b9-009d-419a-aa97-7c3e7724f561.png?v=1674058996&width=240"));
        logo.scaleAbsolute(200f, 100f); // Adjust the size of the logo as needed
        logoCell.addElement(logo);
        companyTable.addCell(logoCell);

        // Add company table to the document
        document.add(companyTable);
        
        
        Paragraph detalleTitle = new Paragraph("Detalle Venta", detalleFont);
        detalleTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(detalleTitle);
        // Add customer name
        Paragraph customerParagraph = new Paragraph("Cliente: " + venta.getCliente().getNombres(), subtitleFont);
        customerParagraph.setAlignment(Element.ALIGN_LEFT);
        document.add(customerParagraph);
        
     // Add customer name
        Paragraph dniParagraph = new Paragraph("Documento Identidad: " + venta.getCliente().getDocumentoIdentidad(), subtitleFont);
        dniParagraph.setAlignment(Element.ALIGN_LEFT);
        document.add(dniParagraph);

        // Add sales details

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell(new PdfPCell(new Phrase("Cantidad", detailBoldFont)));
        table.addCell(new PdfPCell(new Phrase("Producto", detailBoldFont)));
        table.addCell(new PdfPCell(new Phrase("Precio Unitario", detailBoldFont)));
        table.addCell(new PdfPCell(new Phrase("SubTotal", detailBoldFont)));
       

        for (DetalleVenta detalleVenta : detalleVentas) {

            table.addCell(new PdfPCell(new Phrase(String.valueOf(detalleVenta.getCantidad()), detailFont)));
            table.addCell(new PdfPCell(new Phrase(detalleVenta.getProducto().getNombreProducto(), detailFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(detalleVenta.getProducto().getPrecioUnitario()), detailFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(detalleVenta.calcularImporteTotal()), detailFont)));
        }
        
        
        document.add(table);
        
        // Add customer name
        Paragraph totalParagraph = new Paragraph("Total: " + venta.getTotal(), detalleFont);
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalParagraph);
        
        
        // Crear una línea larga de extremo a extremo
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineWidth(1f); // Ancho de la línea
        lineSeparator.setLineColor(BaseColor.BLACK); // Color de la línea
        Paragraph lineParagraph = new Paragraph(new Chunk(lineSeparator));

        // Agregar la línea al documento
        document.add(lineParagraph);
        
        
     // Crear un código QR
        String jsonData = convertirVentaAJSON(venta);
        BarcodeQRCode qrCode = new BarcodeQRCode(jsonData, 1000, 1000, null);
        Image qrCodeImage = qrCode.getImage();

        // Centrar el código QR en el documento
        qrCodeImage.setAlignment(Element.ALIGN_CENTER);
        
        // Establecer el tamaño y la posición del código QR
        qrCodeImage.scaleToFit(100, 100); // Tamaño del código QR
        qrCodeImage.setAbsolutePosition((document.getPageSize().getWidth() - qrCodeImage.getScaledWidth()) / 2,
                (document.getPageSize().getHeight() - qrCodeImage.getScaledHeight()) / 2);

        
        // Agregar el código QR al documento
        document.add(qrCodeImage);
        
        // Close the document
        document.close();
    }
    
    public static String convertirVentaAJSON(Venta venta) {
        return "{\"id\": " + venta.getId() + ", \"total\": " + venta.getTotal()+ ", \"cliente\": " + venta.getCliente().getNombres() + "}";
    }
}
