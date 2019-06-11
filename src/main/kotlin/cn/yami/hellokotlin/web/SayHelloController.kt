package cn.yami.hellokotlin.web

import cn.yami.hellokotlin.common.annotation.Log
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/hello")
@RestController
class SayHelloController {

    @Log
    @RequestMapping("/say")
    fun say(): String {
        return "hello kotlin"
    }

}