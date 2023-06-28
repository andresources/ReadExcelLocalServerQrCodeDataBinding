package com.readanyfile

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    private lateinit var tvFileName: TextView
    private lateinit var iv: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvFileName = findViewById(R.id.tvFileName)
        iv = findViewById(R.id.iv)
        //context.cacheDir.deleteRecursively()
    }

    fun funSubmit(view: View) {
        openDocument()
    }
    private val PICK_FILE = 100
    private fun openDocument() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        resultLauncher.launch(intent)
        //startActivityForResult(intent, PICK_FILE)
    }
    /*var someActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<Any> { result ->
            if (result.getResultCode() === RESULT_OK) {
                // There are no request codes
                val data: Intent = result.getData()
                doSomeOperations()
            }
        })*/
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            var mimeTypeExtension: String? = ""
            result.data?.data?.also { uri ->
                mimeTypeExtension = uri.getExtention(this)
                copyFileAndExtract(uri, mimeTypeExtension.orEmpty())
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE && resultCode == Activity.RESULT_OK) {
            var mimeTypeExtension: String? = ""
            data?.data?.also { uri ->
                mimeTypeExtension = uri.getExtention(this)
                copyFileAndExtract(uri, mimeTypeExtension.orEmpty())
            }
        }
    }
    fun Uri.getExtention(context: Context): String? {
        var extension: String? = ""
        extension = if (this.scheme == ContentResolver.SCHEME_CONTENT) {
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.getContentResolver().getType(this))
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                MimeTypeMap.getFileExtensionFromUrl(FileProvider.getUriForFile(context, context.packageName + ".provider", File(this.path)).toString())
            } else {
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(this.path)).toString())
            }
        }
        return extension
    }
    private var file: File? = null
    private var fileUri: Uri? = null
    private fun copyFileAndExtract(uri: Uri, extension: String) {
        val dir = File(this.filesDir, "doc")
        dir.mkdirs()
        val fileName = getFileName(uri)
        file = File(dir, fileName)
        file?.createNewFile()
        val fout = FileOutputStream(file)
        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                fout.use { output ->
                    inputStream.copyTo(output)
                    output.flush()
                }
            }
            fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file!!)
        } catch (e: Exception) {
            fileUri = uri
            e.printStackTrace()
        }
        fileUri?.apply {
            file?.apply {
                tvFileName.setText("path : ${this.absolutePath}")
                /*var bitmap = BitmapFactory.decodeFile(this.absolutePath)
                iv.setImageBitmap(bitmap)*/
                UploadFileAsync(this.absolutePath).execute("");
            }
        }
    }
    fun getFileName(uri: Uri): String? = when (uri.scheme) {
        ContentResolver.SCHEME_CONTENT -> getContentFileName(uri)
        else -> uri.path?.let(::File)?.name
    }

    private fun getContentFileName(uri: Uri): String? = runCatching {
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            cursor.moveToFirst()
            return@use cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                .let(cursor::getString)
        }
    }.getOrNull()

    fun funDeleteCache(view: View) {
        CoroutineScope(Job()  + Dispatchers.IO).launch {
            //cacheDir.deleteRecursively()
            val dir = File(filesDir, "doc")
            dir.deleteRecursively()
        }
    }
}