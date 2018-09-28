package com.example.kotgra.common

import javax.servlet.http.HttpServletResponse
import java.io.*

object DownloadUtils {
    fun down(res: HttpServletResponse, fileName: String, path: String) {
        res.setHeader("content-type", "application/octet-stream")
        res.contentType = "application/octet-stream"
        res.setHeader("Content-Disposition", "attachment;filename=$fileName")
        val buff = ByteArray(2048)
        var bis: BufferedInputStream? = null
        var os: OutputStream?
        try {
            os = res.outputStream
            bis = BufferedInputStream(FileInputStream(File(path)))
            var i = bis.read(buff)
            while (i != -1) {
                os!!.write(buff, 0, buff.size)
                os.flush()
                i = bis.read(buff)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (bis != null) {
                try {
                    bis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }
}
