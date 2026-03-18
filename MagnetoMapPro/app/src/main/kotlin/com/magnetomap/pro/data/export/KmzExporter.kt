package com.magnetomap.pro.data.export

import com.magnetomap.pro.domain.model.AnomalyDetection
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class KmzExporter {
    fun export(anomalies: List<AnomalyDetection>, file: File) {
        val kmlContent = buildString {
            appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            appendLine("<kml xmlns=\"http://www.opengis.net/kml/2.2\">")
            appendLine("  <Document>")
            appendLine("    <name>MagnetoMap Pro Survey</name>")
            anomalies.forEach { a ->
                appendLine("    <Placemark>")
                appendLine("      <name>Anomaly ${a.classification}</name>")
                appendLine("      <description>Magnitude: ${a.peakMagnitude} nT, Confidence: ${a.confidence}</description>")
                appendLine("      <Point>")
                appendLine("        <coordinates>${a.x},${a.y},${a.z}</coordinates>")
                appendLine("      </Point>")
                appendLine("    </Placemark>")
            }
            appendLine("  </Document>")
            appendLine("</kml>")
        }

        ZipOutputStream(FileOutputStream(file)).use { zos ->
            val entry = ZipEntry("doc.kml")
            zos.putNextEntry(entry)
            zos.write(kmlContent.toByteArray())
            zos.closeEntry()
        }
    }
}
