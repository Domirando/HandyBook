package com.example.handybook.model

import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.io.*


// Function to read a file from a storage path
fun readFileFromStorage(filePath: String): InputStreamReader {
    val file = File(filePath)
    val stringBuilder = StringBuilder()
    val inputStream = FileInputStream(file)
    Log.d("input", inputStream.toString())
    val inputStreamReader = InputStreamReader(inputStream, Charset.defaultCharset())
    Log.d("input reader", inputStreamReader.toString())
    val reader = BufferedReader(inputStreamReader)
    Log.d("input buffered", reader.toString())
    try {

        var line: String?

        // Read each line of the file and append it to a StringBuilder
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append("\n")
        }

        reader.close()
        inputStreamReader.close()
        inputStream.close()
    } catch (e: Exception) {
        // Handle exceptions here
        e.printStackTrace()
    }

    return inputStreamReader
}


// Function to create a file from a FileInputStream
fun createFileFromInputStream(inputStream: FileInputStream, outputPath: String): File? {
    val outputFile = File(outputPath)

    try {
        val outputStream = FileOutputStream(outputFile)
        val buffer = ByteArray(1024)
        var length: Int

        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }

        outputStream.close()
        inputStream.close()

        return outputFile
    } catch (e: IOException) {
        // Handle exceptions here
        e.printStackTrace()
    }

    return null
}

