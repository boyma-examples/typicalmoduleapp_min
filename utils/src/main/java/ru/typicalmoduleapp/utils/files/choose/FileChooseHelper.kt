package ru.typicalmoduleapp.utils.files.choose

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import java.io.*


fun chooseFileFromDir(frag: Fragment, requestCode: Int) {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "*/*"
    intent.addCategory(Intent.CATEGORY_OPENABLE)

    try {
        frag.startActivityForResult(Intent.createChooser(intent, ""), requestCode)
    } catch (e: ActivityNotFoundException) {

    }
}

/*
 * in fragment
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FILE_SELECT_CODE -> data?.data?.let {
                    viewModel.onFileAttach(it)
                }
            }
        }
    }
*/

data class FileData(
    val realFileName: String,
    val mimeType: String,
    val realPath: String,
    val cachedFilePath: String
)

fun getRealNameOfFile(context: Context, uri: Uri): String? {
    if (uri.toString().startsWith("content://")) {
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver
                .query(uri, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor?.close()
        }
    } else if (uri.path!!.startsWith("file://")) {
        uri.path?.let {
            return File(it).name
        }
    }
    return null
}

fun fileDataFromTempedFile(ctx: Context, uri: Uri): FileData? {
    try {
        val realFileName = getRealNameOfFile(ctx, uri) ?: "tmp"
        val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(File(realFileName).extension)
            ?: "text/plain"
        val contentResolver = ctx.contentResolver ?: throw IOException()
        val inp = contentResolver.openInputStream(uri) ?: throw IOException()
        val tempFileName = "tmp" + System.currentTimeMillis().toString()
        val tempFile = File.createTempFile(tempFileName, File(realFileName).extension, ctx.cacheDir)
        writeFully(tempFile, inp)
        return FileData(
            realFileName,
            type,
            uri.toString(),
            tempFile.path
        )
    } catch (e: Exception) {
        return null
    }
}

@Throws(IOException::class)
fun writeFully(to: File, from: InputStream) {
    val buffer = ByteArray(4096)
    var out: OutputStream? = null
    try {
        out = FileOutputStream(to)
        var read: Int
        do {
            read = from.read(buffer)
            if (read != -1) out.write(buffer, 0, read) else break
        } while (true)
    } finally {
        from.close()
        out?.let { out.close() }
    }
}