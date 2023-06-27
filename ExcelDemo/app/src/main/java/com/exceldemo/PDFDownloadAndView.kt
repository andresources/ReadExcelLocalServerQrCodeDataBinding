package com.exceldemo

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.util.*


class PDFDownloadAndView {
    companion object {
        @JvmStatic
        fun downloadViewPDF(context: Context, input: InputStream): String {
            var result = ""
            var job = CoroutineScope(Dispatchers.IO).launch {
                val dir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Harry1039" + Date().time)
                dir.delete()
                try {
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    val dwldsPath: File = File(dir, "Testing.xlsx")

                    if (dwldsPath.exists()) {
                        dwldsPath.delete()
                    }
                    dwldsPath.createNewFile()
                    result = writeStreamToFile(input, dwldsPath)
                } catch (e: Exception) {
                    result = "Error"
                }

            }
            return result
        }


        fun writeStreamToFile(input: InputStream, file: File?): String = try {
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
           // var c = getSheet(input)
            "use : "
        } catch (e: IOException) {
            e.printStackTrace()
            "Eror"
        } finally {
            try {
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }
        }
    }

}