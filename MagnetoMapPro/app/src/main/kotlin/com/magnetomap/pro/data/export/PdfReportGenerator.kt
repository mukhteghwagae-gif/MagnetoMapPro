package com.magnetomap.pro.data.export

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.magnetomap.pro.domain.model.Survey
import java.io.File

class PdfReportGenerator {
    fun generate(survey: Survey, file: File) {
        val writer = PdfWriter(file)
        val pdf = PdfDocument(writer)
        val document = Document(pdf)

        document.add(Paragraph("MagnetoMap Pro - Survey Report").setBold().setFontSize(20f))
        document.add(Paragraph("Survey Name: ${survey.name}"))
        document.add(Paragraph("Samples Collected: ${survey.sampleCount}"))
        document.add(Paragraph("Start Time: ${survey.startTime}"))
        document.add(Paragraph("End Time: ${survey.endTime ?: "N/A"}"))
        
        document.add(Paragraph("\nMethodology Note:"))
        document.add(Paragraph("Data was interpolated using Ordinary Kriging with a spherical variogram model. Anomalies were classified using an on-device TensorFlow Lite model."))

        document.close()
    }
}
