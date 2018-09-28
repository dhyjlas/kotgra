package com.example.kotgra.controller

import com.example.kotgra.common.DownloadUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class TestController {
    @RequestMapping("")
    fun hello(): String {
        return "Hello Kotlin World"
    }

    @RequestMapping("file/{fileName}")
    fun download(res: HttpServletResponse, @PathVariable fileName: String){
        val path = "D://"

        DownloadUtils.down(res, fileName, path + fileName)
    }
}
