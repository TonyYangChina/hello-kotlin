package cn.yami.hellokotlin.common.util

import javax.servlet.http.HttpServletRequest

@Suppress("unused")
object IPUtil {
    fun getIpAddress(request: HttpServletRequest): String {
        var ip = request.getHeader("x-forward-for")

        if (ip == null || ip.isEmpty() || "unknown" == ip.toLowerCase()) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown"== ip.toLowerCase()) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown" == ip.toLowerCase()) {
            ip = request.remoteAddr
        }
        return if ("0:0:0:0:0:0:0:1" == ip) "127.0.0.1" else ip
    }
}