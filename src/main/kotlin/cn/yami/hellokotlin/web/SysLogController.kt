package cn.yami.hellokotlin.web

import cn.yami.hellokotlin.common.constant.Constants
import cn.yami.hellokotlin.common.constant.ErrorCode
import cn.yami.hellokotlin.db.SysLog
import cn.yami.hellokotlin.db.SysLogRepository
import cn.yami.hellokotlin.db.User
import cn.yami.hellokotlin.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RequestMapping("/sys_log")
@RestController
class SysLogController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var sysLogRepository: SysLogRepository

    // private val logger = KotlinLogging.logger {}

    @PostMapping("/list")
    fun logList(request: HttpServletRequest): Map<String, Any> {
        val token: String = request.getHeader(Constants.TOKEN_HEADER_NAME)
        val user: User? = userService.findUserByToken(token)

        user?: return mapOf("ret" to ErrorCode.INVIDATE_TOKEN_CODE, "msg" to "token参数异常")
        val logs = sysLogRepository.findByUserId(user.id)
        val logList = arrayListOf<Map<String, Any>>()
        logs.forEach{
            sysLog: SysLog ->
            logList.add(mapOf("userName" to sysLog.userName, "id" to sysLog.id) as Map<String, Any>)
        }
        return mapOf("ret" to 0, "msg" to "OK", "data" to logList)
    }

}