package cn.yami.hellokotlin.web

import cn.yami.hellokotlin.common.annotation.Log
// import cn.yami.hellokotlin.common.util.joinToString
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/hello")
@RestController
class SayHelloController {

    @Log
    @RequestMapping("/say")
    fun say(): String {
        // joinToString(arrayListOf("2323","122"))
        arrayListOf("2323","122").joinToString()
        return "hello kotlin"
    }

}