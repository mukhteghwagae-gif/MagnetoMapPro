package com.magnetomap.pro.data.export

import com.magnetomap.pro.domain.model.MagneticSample
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter

class CsvExporter {
    fun export(samples: List<MagneticSample>, file: File) {
        val writer = CSVWriter(FileWriter(file))
        val header = arrayOf("timestamp_unix_ms", "x_m", "y_m", "z_m", "Bx_nT", "By_nT", "Bz_nT", "B_mag_nT", "is_contaminated")
        writer.writeNext(header)
        
        samples.forEach { s ->
            val row = arrayOf(
                s.timestamp.toString(),
                s.x.toString(), s.y.toString(), s.z.toString(),
                s.bx.toString(), s.by.toString(), s.bz.toString(),
                s.magnitude.toString(),
                s.isContaminated.toString()
            )
            writer.writeNext(row)
        }
        writer.close()
    }
}
