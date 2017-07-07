package com.cascomio.tesseract.example

import java.io.{BufferedWriter, File, FileFilter, FileWriter}

import net.sourceforge.tess4j.{ITesseract, Tesseract}

class OCRProcessor {
  lazy val tesseract: ITesseract = new Tesseract
  def process(config: Config): Unit = {
    val inputFile = new File(config.filePath)
    val parentFolder = new File(inputFile.getParent)
    val imageFiles = parentFolder.listFiles(new FileFilter {
      override def accept(f: File): Boolean = {
        val path = f.getAbsolutePath
        path.startsWith(config.filePath) &&
          path.toLowerCase().endsWith("png")
      }
    })
    val words = imageFiles.foldLeft("")((acc, f) => {
      acc + " " + tesseract.doOCR(f)
    })

    val writer = new FileWriter(config.fileOuput)
    val buffer = new BufferedWriter(writer)
    buffer.write(words)
    buffer.close()
  }
}
