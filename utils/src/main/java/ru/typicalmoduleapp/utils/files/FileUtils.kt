package ru.typicalmoduleapp.utils.files

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import io.reactivex.Single
import okhttp3.ResponseBody
import okio.Okio
import java.io.File
import java.io.IOException

object FileUtils {

    fun createFile(filename: String?, appctx: Context): File {
        val root = appctx.filesDir
        val directory = File(File(root, "exported"), "files")
        // create target directory if it does not exist
        directory.mkdirs()
        // delete each file in it if possible (logic is: we share single receipt at a time)
        for (dir in directory.listFiles() ?: emptyArray()) {
            try {
                dir.delete()
            } catch (e: Exception) {
            }
        }
        return File(directory, filename ?: "unknown.dat")
    }

    fun openFileByType(file: File, context: Context?) {
        context?.let { mContext ->
            getUriOfFile(file, mContext)?.let { uri ->
                val needGrantReadPermission = "content" == uri.scheme
                val openIntent = Intent(Intent.ACTION_VIEW)
                openIntent.setDataAndType(
                    uri,
                    MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
                )
                if (needGrantReadPermission)
                    openIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                if (openIntent.resolveActivity(mContext.packageManager) != null) {
                    val chooser = Intent.createChooser(openIntent, "Open File")
                    chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext.startActivity(chooser)
                } else {
                    val downloadIntent = Intent(Intent.ACTION_VIEW)
                    downloadIntent.setDataAndType(uri, "text/html")
                    if (needGrantReadPermission)
                        downloadIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                    val chooser = Intent.createChooser(downloadIntent, "Download File")
                    chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext.startActivity(chooser)
                }
            }
        }
    }

    fun clearCache(context: Context) {
        context.cacheDir.deleteRecursively()
    }

    fun getUriOfFile(file: File, ctx: Context): Uri? {
        //uses in app manifest
        return FileProvider.getUriForFile(ctx, "ru.typicalmoduleapp.fileprovider", file)
    }
}

fun Single<ResponseBody>.processResponseBodyToFile(fileDir: File): Single<File> {
    return this.flatMap { responseDto ->
        Single.create<File> { emitter ->
            try {
                val bufferedSink = Okio.buffer(Okio.sink(fileDir))
                bufferedSink.write(responseDto.bytes())
                bufferedSink.close()

                emitter.onSuccess(fileDir)
            } catch (e: IOException) {
                e.printStackTrace()
                emitter.onError(e)
            }
        }
    }
}
